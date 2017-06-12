def FullPerformanceCurve(df: DataFrame, numColumn: String, denColumn: String, sortColumn: String, xAxisColumn: String): DataFrame = {
  
  // This defines a window spec that goes from the top of the list to the current row rank (0)
  val nCumWin = Window.orderBy(col(sortColumn).desc, col(numColumn).desc).rowsBetween(Long.MinValue, 0)
  val dCumWin = Window.orderBy(col(sortColumn).desc, col(numColumn).desc).rowsBetween(Long.MinValue, 0)
  df
    .withColumn("cum_denominator", sum(col(denColumn)).over(dCumWin))
    .withColumn("cum_numerator", sum(col(numColumn)).over(nCumWin))
    .withColumn("performance", expr("if(cum_denominator > 0, cum_numerator/cum_denominator, 0.0)"))
    .withColumn("xAxis", sum(col(xAxisColumn)).over(nCumWin))
    .select('xAxis, 'performance)
}

def computeUniformSampleIndices(df: DataFrame, nth: Int): DataFrame = {
  // Computes a uniformly distributed set of the indices for an array of
  // the same size as the input DataFrame. Includes 1st and last input points
  // always.
  val size = df.count
  // Doing unsafe operations
  val quotient = Math.floor(size/nth).toInt
  val indices: Vector[Long] = (for (i <- 1 to quotient) yield (i*nth).toLong).toVector
  val withStart: Vector[Long] = Vector(1L) ++ indices
  
  val withEndPoints: Vector[Long] = withStart ++ Vector(size)
  
  //val x = List(1,2)
  withEndPoints.toDF("rank")
}

def UniformlySampledPerformanceCurve(df: DataFrame, numColumn: String, denColumn: String, sortColumn: String, xAxisColumn: String, nth: Int): DataFrame = {
  
  // This defines a window spec that goes from the top of the list to the current row rank (0)
  val nCumWin = Window.orderBy(col(sortColumn).desc, col(numColumn).desc).rowsBetween(Long.MinValue, 0)
  val dCumWin = Window.orderBy(col(sortColumn).desc, col(numColumn).desc).rowsBetween(Long.MinValue, 0)
  val rCumWin = Window.orderBy(col(sortColumn).desc, col(numColumn).desc).rowsBetween(Long.MinValue, 0)
  
  // Construct the full performance curve and decorate with rank
  val fullPCWithRank = df
    .withColumn("cum_denominator", sum(col(denColumn)).over(dCumWin))
    .withColumn("cum_numerator", sum(col(numColumn)).over(nCumWin))
    .withColumn("rank", row_number().over(rCumWin))
    .withColumn("performance", expr("if(cum_denominator > 0, cum_numerator/cum_denominator, 0.0)"))
    .withColumn("xAxis", sum(col(xAxisColumn)).over(nCumWin))
    .select('rank, 'xAxis, 'performance)
  
  // Compute the rows wanted
  val wantedRanksDF = computeUniformSampleIndices(fullPCWithRank, nth)
  
  // Get the sampled curve
  fullPCWithRank
    .join(wantedRanksDF, Seq("rank"),"inner")
    .select('xAxis,'performance)
}
