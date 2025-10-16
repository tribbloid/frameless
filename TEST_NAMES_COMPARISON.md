# Detailed Test Comparison: Missing Tests

**Total tests in Current:** 453
**Total tests in Legacy:** 4
**Tests in both:** 4
**Only in Current:** 449
**Only in Legacy:** 0

---

## Complete Test Comparison Table

| Test Name in Legacy | Test Name in Current | Missing In |
|---------------------|----------------------|------------|
| `-` | `frameless.AsTests.as[X2[A, B]]` | **Legacy** |
| `-` | `frameless.AsTests.as[X2[X2[A, B], C]` | **Legacy** |
| `-` | `frameless.BitwiseTests.bitwiseAND` | **Legacy** |
| `-` | `frameless.BitwiseTests.bitwiseOR` | **Legacy** |
| `-` | `frameless.BitwiseTests.bitwiseXOR` | **Legacy** |
| `-` | `frameless.CastTests.cast` | **Legacy** |
| `-` | `frameless.CheckpointTests.checkpoint` | **Legacy** |
| `-` | `frameless.ColTests.col` | **Legacy** |
| `-` | `frameless.ColTests.colMany` | **Legacy** |
| `-` | `frameless.ColTests.select colMany` | **Legacy** |
| `-` | `frameless.CollectTests.collect()` | **Legacy** |
| `-` | `frameless.ColumnTests.Consistency with Spark internal date/time representation` | **Legacy** |
| `-` | `frameless.ColumnTests.asCol` | **Legacy** |
| `-` | `frameless.ColumnTests.asCol single column TypedDatasets` | **Legacy** |
| `-` | `frameless.ColumnTests.asCol with numeric operators` | **Legacy** |
| `-` | `frameless.ColumnTests.between` | **Legacy** |
| `-` | `frameless.ColumnTests.boolean and / or` | **Legacy** |
| `-` | `frameless.ColumnTests.col through lambda` | **Legacy** |
| `-` | `frameless.ColumnTests.contains` | **Legacy** |
| `-` | `frameless.ColumnTests.endsWith` | **Legacy** |
| `-` | `frameless.ColumnTests.field` | **Legacy** |
| `-` | `frameless.ColumnTests.field compiles only for valid field` | **Legacy** |
| `-` | `frameless.ColumnTests.getOrElse` | **Legacy** |
| `-` | `frameless.ColumnTests.like` | **Legacy** |
| `-` | `frameless.ColumnTests.opt` | **Legacy** |
| `-` | `frameless.ColumnTests.opt compiles only for columns of type Option[_]` | **Legacy** |
| `-` | `frameless.ColumnTests.reference Value class so can join on` | **Legacy** |
| `-` | `frameless.ColumnTests.rlike` | **Legacy** |
| `-` | `frameless.ColumnTests.select('a < 'b, 'a <= 'b, 'a > 'b, 'a >= 'b)` | **Legacy** |
| `-` | `frameless.ColumnTests.startsWith` | **Legacy** |
| `-` | `frameless.ColumnTests.substr` | **Legacy** |
| `-` | `frameless.ColumnTests.toString` | **Legacy** |
| `-` | `frameless.ColumnTests.unary_!` | **Legacy** |
| `-` | `frameless.ColumnTests.unary_! with non-boolean columns should not compile` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col((x: MyClass1) => x.a` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col((x: MyClass1) => x.c.e.f` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col((x: MyClass1) => x.toString.size) does not compile` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(_.a)` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(_.a.toString) does not compile` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(_.a.toString.size) does not compile` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(_.c.d)` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(_.c.d) as int does not compile (is long)` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(_.c.e.f)` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(_.g.h does not compile` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(x => java.lang.Math.abs(x.a)) does not compile` | **Legacy** |
| `-` | `frameless.ColumnViaLambdaTests.col(x => x.a` | **Legacy** |
| `-` | `frameless.ColumnsTests.columns` | **Legacy** |
| `-` | `frameless.CountTests.count` | **Legacy** |
| `-` | `frameless.CreateTests.Map fields (scala.Predef.Map / scala.collection.immutable.Map)` | **Legacy** |
| `-` | `frameless.CreateTests.array fields` | **Legacy** |
| `-` | `frameless.CreateTests.creation using X4 derived DataFrames` | **Legacy** |
| `-` | `frameless.CreateTests.dataset with different column order` | **Legacy** |
| `-` | `frameless.CreateTests.list fields` | **Legacy** |
| `-` | `frameless.CreateTests.maps with Option keys should not resolve the TypedEncoder` | **Legacy** |
| `-` | `frameless.CreateTests.not aligned columns should throw an exception` | **Legacy** |
| `-` | `frameless.CreateTests.vector fields` | **Legacy** |
| `-` | `frameless.DistinctTests.distinct` | **Legacy** |
| `-` | `frameless.DropTest.drop four columns` | **Legacy** |
| `-` | `frameless.DropTest.fail to compile on added column name` | **Legacy** |
| `-` | `frameless.DropTest.fail to compile on different column name` | **Legacy** |
| `-` | `frameless.DropTest.fail to compile on missing value` | **Legacy** |
| `-` | `frameless.DropTest.remove column in the middle` | **Legacy** |
| `-` | `frameless.DropTupledTest.drop first column` | **Legacy** |
| `-` | `frameless.DropTupledTest.drop five columns` | **Legacy** |
| `-` | `frameless.DropTupledTest.drop last column` | **Legacy** |
| `-` | `frameless.DropTupledTest.drop middle column` | **Legacy** |
| `-` | `frameless.EncoderTests.It should encode deeply nested collections` | **Legacy** |
| `-` | `frameless.EncoderTests.It should encode java.time.Duration` | **Legacy** |
| `-` | `frameless.EncoderTests.It should encode java.time.Instant` | **Legacy** |
| `-` | `frameless.EncoderTests.It should encode java.time.Period` | **Legacy** |
| `-` | `frameless.ExceptTests.except` | **Legacy** |
| `-` | `frameless.ExplodeTests.explode on arrays` | **Legacy** |
| `-` | `frameless.ExplodeTests.explode on maps` | **Legacy** |
| `-` | `frameless.ExplodeTests.explode on maps making sure no key / value naming collision happens` | **Legacy** |
| `-` | `frameless.ExplodeTests.explode on maps preserving other columns` | **Legacy** |
| `-` | `frameless.ExplodeTests.explode on vectors/list/seq` | **Legacy** |
| `-` | `frameless.ExplodeTests.simple explode test` | **Legacy** |
| `-` | `frameless.FilterTests.Option content filter` | **Legacy** |
| `-` | `frameless.FilterTests.Option equality/inequality for columns` | **Legacy** |
| `-` | `frameless.FilterTests.Option equality/inequality for lit` | **Legacy** |
| `-` | `frameless.FilterTests.filter with arithmetic expressions: addition` | **Legacy** |
| `-` | `frameless.FilterTests.filter with arithmetic expressions: multiplication` | **Legacy** |
| `-` | `frameless.FilterTests.filter with isin values` | **Legacy** |
| `-` | `frameless.FilterTests.filter with values (not columns): addition` | **Legacy** |
| `-` | `frameless.FilterTests.filter('a =!= 'b` | **Legacy** |
| `-` | `frameless.FilterTests.filter('a =!= 'b)` | **Legacy** |
| `-` | `frameless.FilterTests.filter('a =!= lit(b))` | **Legacy** |
| `-` | `frameless.FilterTests.filter('a == lit(b))` | **Legacy** |
| `-` | `frameless.FirstTests.first` | **Legacy** |
| `-` | `frameless.FirstTests.first on empty dataset should return None` | **Legacy** |
| `-` | `frameless.FlattenTests.different Optional types` | **Legacy** |
| `-` | `frameless.FlattenTests.simple flatten test` | **Legacy** |
| `-` | `frameless.GroupByTests.agg(sum('a))` | **Legacy** |
| `-` | `frameless.GroupByTests.agg(sum('a), sum('b))` | **Legacy** |
| `-` | `frameless.GroupByTests.agg(sum('a), sum('b), min('c), max('d))` | **Legacy** |
| `-` | `frameless.GroupByTests.agg(sum('a), sum('b), sum('c))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a).agg(sum('b))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a).agg(sum('b), sum('c)) to groupBy('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a).flatMapGroups(('a, toVector(('a, 'b))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a).mapGroups('a, sum('b))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a).mapGroups(('a, toVector(('a, 'b))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a, 'b).agg(sum('c)) to groupBy('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a, 'b).agg(sum('c), sum('d))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupBy('a, 'b).mapGroups('a, 'b, sum('c))` | **Legacy** |
| `-` | `frameless.GroupByTests.groupByMany('a).agg(sum('b))` | **Legacy** |
| `-` | `frameless.InjectionTests.Derive encoder for ADT with abstract class as the base type` | **Legacy** |
| `-` | `frameless.InjectionTests.Derive encoder for phantom type` | **Legacy** |
| `-` | `frameless.InjectionTests.Derive encoder for type with data constructors defined as parameterless case classes` | **Legacy** |
| `-` | `frameless.InjectionTests.Derive encoder for type with data constructors defined in the companion object` | **Legacy** |
| `-` | `frameless.InjectionTests.Injection based encoders` | **Legacy** |
| `-` | `frameless.InjectionTests.Resolve ambiguity by importing usingDerivation` | **Legacy** |
| `-` | `frameless.InjectionTests.Resolve ambiguity by importing usingInjection` | **Legacy** |
| `-` | `frameless.InjectionTests.Resolve missing implicit by deriving Injection instance` | **Legacy** |
| `-` | `frameless.InjectionTests.TypedEncoder[Employee] implicit is missing` | **Legacy** |
| `-` | `frameless.InjectionTests.TypedEncoder[Maybe] cannot be derived` | **Legacy** |
| `-` | `frameless.InjectionTests.TypedEncoder[Person] is ambiguous` | **Legacy** |
| `-` | `frameless.InjectionTests.apply method of derived Injection instance produces the correct string` | **Legacy** |
| `-` | `frameless.InjectionTests.invert method of derived Injection instance produces the correct value` | **Legacy** |
| `-` | `frameless.InjectionTests.invert method of derived Injection instance should throw exception if string does not match data constructor names` | **Legacy** |
| `-` | `frameless.InputFilesTests.inputFiles` | **Legacy** |
| `-` | `frameless.IntersectTests.intersect` | **Legacy** |
| `-` | `frameless.IsLocalTests.isLocal` | **Legacy** |
| `-` | `frameless.IsStreamingTests.isStreaming` | **Legacy** |
| `frameless.IsValueClassTests.Case class is not Value class` | `frameless.IsValueClassTests.Case class is not Value class` | None (in both) |
| `frameless.IsValueClassTests.Scala value type is not Value class (excluded)` | `frameless.IsValueClassTests.Scala value type is not Value class (excluded)` | None (in both) |
| `frameless.IsValueClassTests.Value class evidence` | `frameless.IsValueClassTests.Value class evidence` | None (in both) |
| `-` | `frameless.JobTests.flatMap associativity` | **Legacy** |
| `-` | `frameless.JobTests.flatMap left identity` | **Legacy** |
| `-` | `frameless.JobTests.flatMap right identity` | **Legacy** |
| `-` | `frameless.JobTests.map composition` | **Legacy** |
| `-` | `frameless.JobTests.map identity` | **Legacy** |
| `-` | `frameless.JobTests.properties read back` | **Legacy** |
| `-` | `frameless.JoinTests.ab.joinCross(ac)` | **Legacy** |
| `-` | `frameless.JoinTests.ab.joinFull(ac)(ab.a == ac.a)` | **Legacy** |
| `-` | `frameless.JoinTests.ab.joinInner(ac)(ab.a == ac.a)` | **Legacy** |
| `-` | `frameless.JoinTests.ab.joinLeft(ac)(ab.a == ac.a)` | **Legacy** |
| `-` | `frameless.JoinTests.ab.joinLeftAnti(ac)(ab.a == ac.a)` | **Legacy** |
| `-` | `frameless.JoinTests.ab.joinLeftSemi(ac)(ab.a == ac.a)` | **Legacy** |
| `-` | `frameless.JoinTests.ab.joinRight(ac)(ab.a == ac.a)` | **Legacy** |
| `-` | `frameless.LimitTests.limit` | **Legacy** |
| `-` | `frameless.LitTests.#205: comparing literals encoded using Injection` | **Legacy** |
| `-` | `frameless.LitTests.select(lit(...))` | **Legacy** |
| `-` | `frameless.LitTests.support optional value class` | **Legacy** |
| `-` | `frameless.LitTests.support value class` | **Legacy** |
| `-` | `frameless.NumericTests.a mod lit(b)` | **Legacy** |
| `-` | `frameless.NumericTests.divide` | **Legacy** |
| `-` | `frameless.NumericTests.divide BigDecimals` | **Legacy** |
| `-` | `frameless.NumericTests.isNaN` | **Legacy** |
| `-` | `frameless.NumericTests.isNaN with non-nan types should not compile` | **Legacy** |
| `-` | `frameless.NumericTests.minus` | **Legacy** |
| `-` | `frameless.NumericTests.mod` | **Legacy** |
| `-` | `frameless.NumericTests.multiply` | **Legacy** |
| `-` | `frameless.NumericTests.multiply BigDecimal` | **Legacy** |
| `-` | `frameless.NumericTests.plus` | **Legacy** |
| `-` | `frameless.OrderByTests.derives a CatalystOrdered for case classes when all fields are comparable` | **Legacy** |
| `-` | `frameless.OrderByTests.derives a CatalystOrdered for tuples when all fields are comparable` | **Legacy** |
| `-` | `frameless.OrderByTests.fail when selected column is not sortable` | **Legacy** |
| `-` | `frameless.OrderByTests.fails to compile when one of the field isn't comparable` | **Legacy** |
| `-` | `frameless.OrderByTests.single column non nullable orderBy` | **Legacy** |
| `-` | `frameless.OrderByTests.single column non nullable partition sorting` | **Legacy** |
| `-` | `frameless.OrderByTests.sort support for mixed default and explicit ordering` | **Legacy** |
| `-` | `frameless.OrderByTests.three columns non nullable orderBy` | **Legacy** |
| `-` | `frameless.OrderByTests.three columns non nullable partition sorting` | **Legacy** |
| `-` | `frameless.OrderByTests.two columns non nullable orderBy` | **Legacy** |
| `-` | `frameless.OrderByTests.two columns non nullable partition sorting` | **Legacy** |
| `-` | `frameless.QueryExecutionTests.queryExecution` | **Legacy** |
| `-` | `frameless.RandomSplitTests.randomSplit(weight, seed)` | **Legacy** |
| `-` | `frameless.RandomSplitTests.randomSplitAsList(weight, seed)` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Case class with Map & Value class` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Case class with simple Map` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Case class with value class as optional field` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Case class with value class field` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Deeply nested optional values have correct deserialization` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Dropping fields` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Empty nested record value becomes none on deserialization` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Empty nested record value becomes null on serialization` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Encode array of Value class` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Encode binary array` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Encode case class with Value class` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Encode case class with simple Seq` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Encode simple array` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Nesting with Seq` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Nesting with Set` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Representation skips units` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Scalar value class` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Serialization skips units` | **Legacy** |
| `-` | `frameless.RecordEncoderTests.Unable to encode products made from units only` | **Legacy** |
| `-` | `frameless.RefinedFieldEncoderTests.Encode a bare refined type` | **Legacy** |
| `-` | `frameless.RefinedFieldEncoderTests.Encode case class with a refined field` | **Legacy** |
| `-` | `frameless.RefinedFieldEncoderTests.Encode case class with a refined optional field` | **Legacy** |
| `-` | `frameless.SQLContextTests.sqlContext` | **Legacy** |
| `-` | `frameless.SchemaTests.schema of groupBy('a).agg(sum('b))` | **Legacy** |
| `-` | `frameless.SchemaTests.schema of select(lit(1L))` | **Legacy** |
| `-` | `frameless.SchemaTests.schema of select(lit(1L), lit(2L)).as[X2[Long, Long]]` | **Legacy** |
| `-` | `frameless.SelectTests.select with aggregation operations is not supported` | **Legacy** |
| `-` | `frameless.SelectTests.select with column expression addition` | **Legacy** |
| `-` | `frameless.SelectTests.select with column expression division` | **Legacy** |
| `-` | `frameless.SelectTests.select with column expression multiplication` | **Legacy** |
| `-` | `frameless.SelectTests.select with column expression subtraction` | **Legacy** |
| `-` | `frameless.SelectTests.select('a) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a, 'b) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a, 'b, 'c) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a,'b,'c,'d) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a,'b,'c,'d,'a) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a,'b,'c,'d,'a, 'c) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a,'b,'c,'d,'a,'c,'b) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a,'b,'c,'d,'a,'c,'b, 'a) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a,'b,'c,'d,'a,'c,'b,'a,'c) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a,'b,'c,'d,'a,'c,'b,'a,'c, 'd) FROM abcd` | **Legacy** |
| `-` | `frameless.SelectTests.select('a.b)` | **Legacy** |
| `-` | `frameless.SelectTests.tests to cover problematic dataframe column names during projections` | **Legacy** |
| `-` | `frameless.SelectTests.unary - on arithmetic` | **Legacy** |
| `-` | `frameless.SelectTests.unary - on strings should not type check` | **Legacy** |
| `-` | `frameless.SelfJoinTests.Do you want ambiguous self join? This is how you get ambiguous self join.` | **Legacy** |
| `-` | `frameless.SelfJoinTests.colLeft and colRight are equivalent to col outside of joins` | **Legacy** |
| `-` | `frameless.SelfJoinTests.colLeft and colRight are equivalent to col outside of joins - via files (codegen)` | **Legacy** |
| `-` | `frameless.SelfJoinTests.self join with colLeft/colRight disambiguation` | **Legacy** |
| `-` | `frameless.SelfJoinTests.self join with unambiguous expression` | **Legacy** |
| `-` | `frameless.SelfJoinTests.trivial self join` | **Legacy** |
| `-` | `frameless.SparkSessionTests.sparkSession` | **Legacy** |
| `-` | `frameless.StorageLevelTests.storageLevel` | **Legacy** |
| `-` | `frameless.TakeTests.take` | **Legacy** |
| `-` | `frameless.ToJSONTests.toJSON` | **Legacy** |
| `-` | `frameless.ToLocalIteratorTests.toLocalIterator` | **Legacy** |
| `-` | `frameless.UnionTests.Align fields for case classes` | **Legacy** |
| `-` | `frameless.UnionTests.Align fields for different number of columns` | **Legacy** |
| `-` | `frameless.UnionTests.Union for simple data types` | **Legacy** |
| `-` | `frameless.UnionTests.fail to compile on not aligned schema` | **Legacy** |
| `-` | `frameless.WithColumnTest.append four columns` | **Legacy** |
| `-` | `frameless.WithColumnTest.fail to compile on added column name` | **Legacy** |
| `-` | `frameless.WithColumnTest.fail to compile on different column name` | **Legacy** |
| `-` | `frameless.WithColumnTest.fail to compile on missing value` | **Legacy** |
| `-` | `frameless.WithColumnTest.fail to compile on wrong typed column` | **Legacy** |
| `-` | `frameless.WithColumnTest.update in place` | **Legacy** |
| `-` | `frameless.WithColumnTupledTest.append five columns` | **Legacy** |
| `-` | `frameless.WriteStreamTests.write csv` | **Legacy** |
| `-` | `frameless.WriteStreamTests.write parquet` | **Legacy** |
| `-` | `frameless.WriteTests.write csv` | **Legacy** |
| `-` | `frameless.WriteTests.write parquet` | **Legacy** |
| `-` | `frameless.cats.FramelessSyntaxTests.dataset typed - toTyped` | **Legacy** |
| `-` | `frameless.cats.FramelessSyntaxTests.properties can be read back` | **Legacy** |
| `-` | `frameless.cats.Test.inner pairwise monoid` | **Legacy** |
| `-` | `frameless.cats.Test.pair rdd numeric commutative semigroup example` | **Legacy** |
| `-` | `frameless.cats.Test.rdd of SortedMap[Int,Int] commutative monoid` | **Legacy** |
| `-` | `frameless.cats.Test.rdd simple numeric commutative semigroup` | **Legacy** |
| `-` | `frameless.cats.Test.rdd tuple commutative semigroup example` | **Legacy** |
| `frameless.cats.Test.spark is working` | `frameless.cats.Test.spark is working` | None (in both) |
| `-` | `frameless.forward.ForeachTests.foreach` | **Legacy** |
| `-` | `frameless.forward.ForeachTests.foreachPartition` | **Legacy** |
| `-` | `frameless.forward.HeadTests.headOption(), head(1), and head(4)` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.approxCountDistinct` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.avg` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.collectList` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.collectSet` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.corr` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.count` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.count('a)` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.countDistinct` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.covar_pop` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.covar_samp` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.first` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.kurtosis` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.last` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.lit` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.litAggr` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.max` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.max with follow up multiplication` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.min` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.skewness` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.stddev and variance` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.stddev_pop` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.stddev_samp` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.sum` | **Legacy** |
| `-` | `frameless.functions.AggregateFunctionsTests.sumDistinct` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.Empty vararg tests` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.abs` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.abs big decimal` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.acos` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.arrayContains` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.ascii` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.asin` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.atan` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.atan2` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.atan2LitLeft` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.atan2LitRight` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.base64` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.bin` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.bitwiseNOT` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.bround` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.bround big decimal` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.bround big decimal with scale` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.bround with scale` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.ceil` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.concat` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.concat for TypedAggregate` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.concat_ws` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.concat_ws for TypedAggregate` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.conv` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.cos` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.cosh` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.crbt` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.crc32` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.dayofmonth` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.dayofweek` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.dayofyear` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.degrees` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.exp` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.factorial` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.floor` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.hour` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.hypot with double` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.hypot with two columns` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.inputFileName` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.instr` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.length` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.levenshtein` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.log` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.log with base` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.log10` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.log1p` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.log2` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.lower` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.lpad` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.ltrim` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.md5` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.minute` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.monotonic id` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.month` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.negate` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.not` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.pmod` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.pow with double` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.pow with two columns` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.quarter` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.regexp_replace` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.reverse` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.round` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.round big decimal` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.round big decimal with scale` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.round with scale` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.rpad` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.rtrim` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.second` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.sha1` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.sha2` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.shiftLeft` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.shiftRight` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.shiftRightUnsigned` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.signum` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.sin` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.sinh` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.sqrt` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.substring` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.tan` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.tanh` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.trim` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.unbase64` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.upper` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.weekofyear` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.when` | **Legacy** |
| `-` | `frameless.functions.NonAggregateFunctionsTests.year` | **Legacy** |
| `-` | `frameless.functions.UdfTests.five argument udf` | **Legacy** |
| `-` | `frameless.functions.UdfTests.four argument udf` | **Legacy** |
| `-` | `frameless.functions.UdfTests.multiple one argument udf` | **Legacy** |
| `-` | `frameless.functions.UdfTests.multiple two argument udf` | **Legacy** |
| `-` | `frameless.functions.UdfTests.one argument udf` | **Legacy** |
| `-` | `frameless.functions.UdfTests.three argument udf` | **Legacy** |
| `-` | `frameless.functions.UdfTests.two argument udf` | **Legacy** |
| `-` | `frameless.functions.UnaryFunctionsTest.size on Map` | **Legacy** |
| `-` | `frameless.functions.UnaryFunctionsTest.size on array test` | **Legacy** |
| `-` | `frameless.functions.UnaryFunctionsTest.size tests` | **Legacy** |
| `-` | `frameless.functions.UnaryFunctionsTest.sort in ascending order` | **Legacy** |
| `-` | `frameless.functions.UnaryFunctionsTest.sort in descending order` | **Legacy** |
| `-` | `frameless.functions.UnaryFunctionsTest.sort on array test: ascending order` | **Legacy** |
| `-` | `frameless.functions.UnaryFunctionsTest.sort on array test: descending order` | **Legacy** |
| `-` | `frameless.ml.TypedEncoderInstancesTests.Matrix encoding is injective using collect()` | **Legacy** |
| `-` | `frameless.ml.TypedEncoderInstancesTests.Vector encoding is injective using collect()` | **Legacy** |
| `-` | `frameless.ml.TypedEncoderInstancesTests.Vector is encoded as VectorUDT and thus can be run in a Spark ML model` | **Legacy** |
| `-` | `frameless.ml.classification.ClassificationIntegrationTests.predict field3 from field1 and field2 using a RandomForestClassifier` | **Legacy** |
| `-` | `frameless.ml.classification.TypedRandomForestClassifierTests.create() compiles only with correct inputs` | **Legacy** |
| `-` | `frameless.ml.classification.TypedRandomForestClassifierTests.fit() returns a correct TypedTransformer` | **Legacy** |
| `-` | `frameless.ml.classification.TypedRandomForestClassifierTests.param setting is retained` | **Legacy** |
| `-` | `frameless.ml.clustering.BisectingKMeansTests.fit() returns a correct TypedTransformer` | **Legacy** |
| `-` | `frameless.ml.clustering.BisectingKMeansTests.param setting is retained` | **Legacy** |
| `-` | `frameless.ml.clustering.ClusteringIntegrationTests.predict field2 from field1 using a K-means clustering` | **Legacy** |
| `-` | `frameless.ml.clustering.ClusteringIntegrationTests.predict field2 from field1 using a bisecting K-means clustering` | **Legacy** |
| `-` | `frameless.ml.clustering.KMeansTests.fit() returns a correct TypedTransformer` | **Legacy** |
| `-` | `frameless.ml.clustering.KMeansTests.param setting is retained` | **Legacy** |
| `-` | `frameless.ml.feature.TypedIndexToStringTests..transform() correctly transform an input dataset` | **Legacy** |
| `-` | `frameless.ml.feature.TypedIndexToStringTests.create() compiles only with correct inputs` | **Legacy** |
| `-` | `frameless.ml.feature.TypedStringIndexerTests..fit() returns a correct TypedTransformer` | **Legacy** |
| `-` | `frameless.ml.feature.TypedStringIndexerTests.create() compiles only with correct inputs` | **Legacy** |
| `-` | `frameless.ml.feature.TypedStringIndexerTests.param setting is retained` | **Legacy** |
| `-` | `frameless.ml.feature.TypedVectorAssemblerTests..transform() returns a correct TypedTransformer` | **Legacy** |
| `-` | `frameless.ml.feature.TypedVectorAssemblerTests.create() compiles only with correct inputs` | **Legacy** |
| `-` | `frameless.ml.regression.RegressionIntegrationTests.predict field3 from field1 and field2 using a RandomForestRegressor` | **Legacy** |
| `-` | `frameless.ml.regression.TypedLinearRegressionTests.TypedLinearRegressor should fit straight line` | **Legacy** |
| `-` | `frameless.ml.regression.TypedLinearRegressionTests.create() compiles only with correct inputs` | **Legacy** |
| `-` | `frameless.ml.regression.TypedLinearRegressionTests.fit() returns a correct TypedTransformer` | **Legacy** |
| `-` | `frameless.ml.regression.TypedLinearRegressionTests.param setting is retained` | **Legacy** |
| `-` | `frameless.ml.regression.TypedRandomForestRegressorTests.create() compiles only with correct inputs` | **Legacy** |
| `-` | `frameless.ml.regression.TypedRandomForestRegressorTests.fit() returns a correct TypedTransformer` | **Legacy** |
| `-` | `frameless.ml.regression.TypedRandomForestRegressorTests.param setting is retained` | **Legacy** |
| `-` | `frameless.ops.ColumnTypesTest.test summoning` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a).agg(count())` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a).agg(sum('b)` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a).agg(sum('b), sum('c)) to cube('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a).flatMapGroups(('a, toVector(('a, 'b))` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a).mapGroups('a, sum('b))` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a).mapGroups(('a, toVector(('a, 'b))` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a, 'b).agg(count())` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a, 'b).agg(sum('c)) to cube('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a, 'b).agg(sum('c), sum('d))` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cube('a, 'b).mapGroups('a, 'b, sum('c))` | **Legacy** |
| `-` | `frameless.ops.CubeTests.cubeMany('a).agg(sum('b))` | **Legacy** |
| `-` | `frameless.ops.PivotTest.Pivot on Boolean` | **Legacy** |
| `-` | `frameless.ops.PivotTest.Pivot with cube on Boolean` | **Legacy** |
| `-` | `frameless.ops.PivotTest.Pivot with cube on two columns, pivot on Long` | **Legacy** |
| `-` | `frameless.ops.PivotTest.Pivot with groupBy on two columns, pivot on Long` | **Legacy** |
| `-` | `frameless.ops.PivotTest.Pivot with rollup on Boolean` | **Legacy** |
| `-` | `frameless.ops.PivotTest.Pivot with rollup on two columns, pivot on Long` | **Legacy** |
| `-` | `frameless.ops.PivotTest.X4[Boolean, String, Int, Boolean] pivot on String` | **Legacy** |
| `-` | `frameless.ops.RepeatTest.ill typed` | **Legacy** |
| `-` | `frameless.ops.RepeatTest.summoning with implicitly` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a).agg(count())` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a).agg(sum('b)` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a).agg(sum('b), sum('c)) to rollup('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a).flatMapGroups(('a, toVector(('a, 'b))` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a).mapGroups('a, sum('b))` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a).mapGroups(('a, toVector(('a, 'b))` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a, 'b).agg(count())` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a, 'b).agg(sum('c)) to rollup('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a, 'b).agg(sum('c), sum('d))` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollup('a, 'b).mapGroups('a, 'b, sum('c))` | **Legacy** |
| `-` | `frameless.ops.RollupTests.rollupMany('a).agg(sum('b))` | **Legacy** |
| `-` | `frameless.ops.SmartProjectTest.X3U to X1,X2,X3 projections` | **Legacy** |
| `-` | `frameless.ops.SmartProjectTest.X4 to X1,X2,X3,X4 projections` | **Legacy** |
| `-` | `frameless.ops.SmartProjectTest.project Foo to Bar` | **Legacy** |
| `-` | `frameless.ops.SmartProjectTest.project to InvalidFooProjection should not type check` | **Legacy** |
| `-` | `frameless.ops.deserialized.FilterTests.filter` | **Legacy** |
| `-` | `frameless.ops.deserialized.FlatMapTests.flatMap` | **Legacy** |
| `-` | `frameless.ops.deserialized.MapPartitionsTests.mapPartitions` | **Legacy** |
| `-` | `frameless.ops.deserialized.MapTests.map` | **Legacy** |
| `-` | `frameless.ops.deserialized.ReduceTests.reduce Int` | **Legacy** |
| `-` | `frameless.ops.deserialized.ReduceTests.reduce String` | **Legacy** |
| `-` | `frameless.sql.rules.FramelessLitPushDownTests.java.sql.Timestamp push-down` | **Legacy** |
| `-` | `frameless.sql.rules.FramelessLitPushDownTests.java.time.Instant push-down` | **Legacy** |
| `-` | `frameless.sql.rules.FramelessLitPushDownTests.struct push-down` | **Legacy** |
| `-` | `frameless.syntax.FramelessSyntaxTests.dataset typed - toTyped` | **Legacy** |
| `-` | `frameless.syntax.FramelessSyntaxTests.frameless typed column and aggregate` | **Legacy** |

---

## Tests Only in Current Project (New Tests)

### Module: `cats-spark40` (7 new tests)

| Test Class | Test Name |
|------------|----------|
| `frameless.cats.FramelessSyntaxTests` | `dataset typed - toTyped` |
| `frameless.cats.FramelessSyntaxTests` | `properties can be read back` |
| `frameless.cats.Test` | `inner pairwise monoid` |
| `frameless.cats.Test` | `pair rdd numeric commutative semigroup example` |
| `frameless.cats.Test` | `rdd of SortedMap[Int,Int] commutative monoid` |
| `frameless.cats.Test` | `rdd simple numeric commutative semigroup` |
| `frameless.cats.Test` | `rdd tuple commutative semigroup example` |

### Module: `dataset-spark40` (411 new tests)

| Test Class | Test Name |
|------------|----------|
| `frameless.AsTests` | `as[X2[A, B]]` |
| `frameless.AsTests` | `as[X2[X2[A, B], C]` |
| `frameless.BitwiseTests` | `bitwiseAND` |
| `frameless.BitwiseTests` | `bitwiseOR` |
| `frameless.BitwiseTests` | `bitwiseXOR` |
| `frameless.CastTests` | `cast` |
| `frameless.CheckpointTests` | `checkpoint` |
| `frameless.ColTests` | `col` |
| `frameless.ColTests` | `colMany` |
| `frameless.ColTests` | `select colMany` |
| `frameless.CollectTests` | `collect()` |
| `frameless.ColumnTests` | `Consistency with Spark internal date/time representation` |
| `frameless.ColumnTests` | `asCol` |
| `frameless.ColumnTests` | `asCol single column TypedDatasets` |
| `frameless.ColumnTests` | `asCol with numeric operators` |
| `frameless.ColumnTests` | `between` |
| `frameless.ColumnTests` | `boolean and / or` |
| `frameless.ColumnTests` | `col through lambda` |
| `frameless.ColumnTests` | `contains` |
| `frameless.ColumnTests` | `endsWith` |
| `frameless.ColumnTests` | `field` |
| `frameless.ColumnTests` | `field compiles only for valid field` |
| `frameless.ColumnTests` | `getOrElse` |
| `frameless.ColumnTests` | `like` |
| `frameless.ColumnTests` | `opt` |
| `frameless.ColumnTests` | `opt compiles only for columns of type Option[_]` |
| `frameless.ColumnTests` | `reference Value class so can join on` |
| `frameless.ColumnTests` | `rlike` |
| `frameless.ColumnTests` | `select('a < 'b, 'a <= 'b, 'a > 'b, 'a >= 'b)` |
| `frameless.ColumnTests` | `startsWith` |
| `frameless.ColumnTests` | `substr` |
| `frameless.ColumnTests` | `toString` |
| `frameless.ColumnTests` | `unary_!` |
| `frameless.ColumnTests` | `unary_! with non-boolean columns should not compile` |
| `frameless.ColumnViaLambdaTests` | `col((x: MyClass1) => x.a` |
| `frameless.ColumnViaLambdaTests` | `col((x: MyClass1) => x.c.e.f` |
| `frameless.ColumnViaLambdaTests` | `col((x: MyClass1) => x.toString.size) does not compile` |
| `frameless.ColumnViaLambdaTests` | `col(_.a)` |
| `frameless.ColumnViaLambdaTests` | `col(_.a.toString) does not compile` |
| `frameless.ColumnViaLambdaTests` | `col(_.a.toString.size) does not compile` |
| `frameless.ColumnViaLambdaTests` | `col(_.c.d)` |
| `frameless.ColumnViaLambdaTests` | `col(_.c.d) as int does not compile (is long)` |
| `frameless.ColumnViaLambdaTests` | `col(_.c.e.f)` |
| `frameless.ColumnViaLambdaTests` | `col(_.g.h does not compile` |
| `frameless.ColumnViaLambdaTests` | `col(x => java.lang.Math.abs(x.a)) does not compile` |
| `frameless.ColumnViaLambdaTests` | `col(x => x.a` |
| `frameless.ColumnsTests` | `columns` |
| `frameless.CountTests` | `count` |
| `frameless.CreateTests` | `Map fields (scala.Predef.Map / scala.collection.immutable.Map)` |
| `frameless.CreateTests` | `array fields` |
| `frameless.CreateTests` | `creation using X4 derived DataFrames` |
| `frameless.CreateTests` | `dataset with different column order` |
| `frameless.CreateTests` | `list fields` |
| `frameless.CreateTests` | `maps with Option keys should not resolve the TypedEncoder` |
| `frameless.CreateTests` | `not aligned columns should throw an exception` |
| `frameless.CreateTests` | `vector fields` |
| `frameless.DistinctTests` | `distinct` |
| `frameless.DropTest` | `drop four columns` |
| `frameless.DropTest` | `fail to compile on added column name` |
| `frameless.DropTest` | `fail to compile on different column name` |
| `frameless.DropTest` | `fail to compile on missing value` |
| `frameless.DropTest` | `remove column in the middle` |
| `frameless.DropTupledTest` | `drop first column` |
| `frameless.DropTupledTest` | `drop five columns` |
| `frameless.DropTupledTest` | `drop last column` |
| `frameless.DropTupledTest` | `drop middle column` |
| `frameless.EncoderTests` | `It should encode deeply nested collections` |
| `frameless.EncoderTests` | `It should encode java.time.Duration` |
| `frameless.EncoderTests` | `It should encode java.time.Instant` |
| `frameless.EncoderTests` | `It should encode java.time.Period` |
| `frameless.ExceptTests` | `except` |
| `frameless.ExplodeTests` | `explode on arrays` |
| `frameless.ExplodeTests` | `explode on maps` |
| `frameless.ExplodeTests` | `explode on maps making sure no key / value naming collision happens` |
| `frameless.ExplodeTests` | `explode on maps preserving other columns` |
| `frameless.ExplodeTests` | `explode on vectors/list/seq` |
| `frameless.ExplodeTests` | `simple explode test` |
| `frameless.FilterTests` | `Option content filter` |
| `frameless.FilterTests` | `Option equality/inequality for columns` |
| `frameless.FilterTests` | `Option equality/inequality for lit` |
| `frameless.FilterTests` | `filter with arithmetic expressions: addition` |
| `frameless.FilterTests` | `filter with arithmetic expressions: multiplication` |
| `frameless.FilterTests` | `filter with isin values` |
| `frameless.FilterTests` | `filter with values (not columns): addition` |
| `frameless.FilterTests` | `filter('a =!= 'b` |
| `frameless.FilterTests` | `filter('a =!= 'b)` |
| `frameless.FilterTests` | `filter('a =!= lit(b))` |
| `frameless.FilterTests` | `filter('a == lit(b))` |
| `frameless.FirstTests` | `first` |
| `frameless.FirstTests` | `first on empty dataset should return None` |
| `frameless.FlattenTests` | `different Optional types` |
| `frameless.FlattenTests` | `simple flatten test` |
| `frameless.GroupByTests` | `agg(sum('a))` |
| `frameless.GroupByTests` | `agg(sum('a), sum('b))` |
| `frameless.GroupByTests` | `agg(sum('a), sum('b), min('c), max('d))` |
| `frameless.GroupByTests` | `agg(sum('a), sum('b), sum('c))` |
| `frameless.GroupByTests` | `groupBy('a).agg(sum('b))` |
| `frameless.GroupByTests` | `groupBy('a).agg(sum('b), sum('c)) to groupBy('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))` |
| `frameless.GroupByTests` | `groupBy('a).flatMapGroups(('a, toVector(('a, 'b))` |
| `frameless.GroupByTests` | `groupBy('a).mapGroups('a, sum('b))` |
| `frameless.GroupByTests` | `groupBy('a).mapGroups(('a, toVector(('a, 'b))` |
| `frameless.GroupByTests` | `groupBy('a, 'b).agg(sum('c)) to groupBy('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))` |
| `frameless.GroupByTests` | `groupBy('a, 'b).agg(sum('c), sum('d))` |
| `frameless.GroupByTests` | `groupBy('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))` |
| `frameless.GroupByTests` | `groupBy('a, 'b).mapGroups('a, 'b, sum('c))` |
| `frameless.GroupByTests` | `groupByMany('a).agg(sum('b))` |
| `frameless.InjectionTests` | `Derive encoder for ADT with abstract class as the base type` |
| `frameless.InjectionTests` | `Derive encoder for phantom type` |
| `frameless.InjectionTests` | `Derive encoder for type with data constructors defined as parameterless case classes` |
| `frameless.InjectionTests` | `Derive encoder for type with data constructors defined in the companion object` |
| `frameless.InjectionTests` | `Injection based encoders` |
| `frameless.InjectionTests` | `Resolve ambiguity by importing usingDerivation` |
| `frameless.InjectionTests` | `Resolve ambiguity by importing usingInjection` |
| `frameless.InjectionTests` | `Resolve missing implicit by deriving Injection instance` |
| `frameless.InjectionTests` | `TypedEncoder[Employee] implicit is missing` |
| `frameless.InjectionTests` | `TypedEncoder[Maybe] cannot be derived` |
| `frameless.InjectionTests` | `TypedEncoder[Person] is ambiguous` |
| `frameless.InjectionTests` | `apply method of derived Injection instance produces the correct string` |
| `frameless.InjectionTests` | `invert method of derived Injection instance produces the correct value` |
| `frameless.InjectionTests` | `invert method of derived Injection instance should throw exception if string does not match data constructor names` |
| `frameless.InputFilesTests` | `inputFiles` |
| `frameless.IntersectTests` | `intersect` |
| `frameless.IsLocalTests` | `isLocal` |
| `frameless.IsStreamingTests` | `isStreaming` |
| `frameless.JobTests` | `flatMap associativity` |
| `frameless.JobTests` | `flatMap left identity` |
| `frameless.JobTests` | `flatMap right identity` |
| `frameless.JobTests` | `map composition` |
| `frameless.JobTests` | `map identity` |
| `frameless.JobTests` | `properties read back` |
| `frameless.JoinTests` | `ab.joinCross(ac)` |
| `frameless.JoinTests` | `ab.joinFull(ac)(ab.a == ac.a)` |
| `frameless.JoinTests` | `ab.joinInner(ac)(ab.a == ac.a)` |
| `frameless.JoinTests` | `ab.joinLeft(ac)(ab.a == ac.a)` |
| `frameless.JoinTests` | `ab.joinLeftAnti(ac)(ab.a == ac.a)` |
| `frameless.JoinTests` | `ab.joinLeftSemi(ac)(ab.a == ac.a)` |
| `frameless.JoinTests` | `ab.joinRight(ac)(ab.a == ac.a)` |
| `frameless.LimitTests` | `limit` |
| `frameless.LitTests` | `#205: comparing literals encoded using Injection` |
| `frameless.LitTests` | `select(lit(...))` |
| `frameless.LitTests` | `support optional value class` |
| `frameless.LitTests` | `support value class` |
| `frameless.NumericTests` | `a mod lit(b)` |
| `frameless.NumericTests` | `divide` |
| `frameless.NumericTests` | `divide BigDecimals` |
| `frameless.NumericTests` | `isNaN` |
| `frameless.NumericTests` | `isNaN with non-nan types should not compile` |
| `frameless.NumericTests` | `minus` |
| `frameless.NumericTests` | `mod` |
| `frameless.NumericTests` | `multiply` |
| `frameless.NumericTests` | `multiply BigDecimal` |
| `frameless.NumericTests` | `plus` |
| `frameless.OrderByTests` | `derives a CatalystOrdered for case classes when all fields are comparable` |
| `frameless.OrderByTests` | `derives a CatalystOrdered for tuples when all fields are comparable` |
| `frameless.OrderByTests` | `fail when selected column is not sortable` |
| `frameless.OrderByTests` | `fails to compile when one of the field isn't comparable` |
| `frameless.OrderByTests` | `single column non nullable orderBy` |
| `frameless.OrderByTests` | `single column non nullable partition sorting` |
| `frameless.OrderByTests` | `sort support for mixed default and explicit ordering` |
| `frameless.OrderByTests` | `three columns non nullable orderBy` |
| `frameless.OrderByTests` | `three columns non nullable partition sorting` |
| `frameless.OrderByTests` | `two columns non nullable orderBy` |
| `frameless.OrderByTests` | `two columns non nullable partition sorting` |
| `frameless.QueryExecutionTests` | `queryExecution` |
| `frameless.RandomSplitTests` | `randomSplit(weight, seed)` |
| `frameless.RandomSplitTests` | `randomSplitAsList(weight, seed)` |
| `frameless.RecordEncoderTests` | `Case class with Map & Value class` |
| `frameless.RecordEncoderTests` | `Case class with simple Map` |
| `frameless.RecordEncoderTests` | `Case class with value class as optional field` |
| `frameless.RecordEncoderTests` | `Case class with value class field` |
| `frameless.RecordEncoderTests` | `Deeply nested optional values have correct deserialization` |
| `frameless.RecordEncoderTests` | `Dropping fields` |
| `frameless.RecordEncoderTests` | `Empty nested record value becomes none on deserialization` |
| `frameless.RecordEncoderTests` | `Empty nested record value becomes null on serialization` |
| `frameless.RecordEncoderTests` | `Encode array of Value class` |
| `frameless.RecordEncoderTests` | `Encode binary array` |
| `frameless.RecordEncoderTests` | `Encode case class with Value class` |
| `frameless.RecordEncoderTests` | `Encode case class with simple Seq` |
| `frameless.RecordEncoderTests` | `Encode simple array` |
| `frameless.RecordEncoderTests` | `Nesting with Seq` |
| `frameless.RecordEncoderTests` | `Nesting with Set` |
| `frameless.RecordEncoderTests` | `Representation skips units` |
| `frameless.RecordEncoderTests` | `Scalar value class` |
| `frameless.RecordEncoderTests` | `Serialization skips units` |
| `frameless.RecordEncoderTests` | `Unable to encode products made from units only` |
| `frameless.SQLContextTests` | `sqlContext` |
| `frameless.SchemaTests` | `schema of groupBy('a).agg(sum('b))` |
| `frameless.SchemaTests` | `schema of select(lit(1L))` |
| `frameless.SchemaTests` | `schema of select(lit(1L), lit(2L)).as[X2[Long, Long]]` |
| `frameless.SelectTests` | `select with aggregation operations is not supported` |
| `frameless.SelectTests` | `select with column expression addition` |
| `frameless.SelectTests` | `select with column expression division` |
| `frameless.SelectTests` | `select with column expression multiplication` |
| `frameless.SelectTests` | `select with column expression subtraction` |
| `frameless.SelectTests` | `select('a) FROM abcd` |
| `frameless.SelectTests` | `select('a, 'b) FROM abcd` |
| `frameless.SelectTests` | `select('a, 'b, 'c) FROM abcd` |
| `frameless.SelectTests` | `select('a,'b,'c,'d) FROM abcd` |
| `frameless.SelectTests` | `select('a,'b,'c,'d,'a) FROM abcd` |
| `frameless.SelectTests` | `select('a,'b,'c,'d,'a, 'c) FROM abcd` |
| `frameless.SelectTests` | `select('a,'b,'c,'d,'a,'c,'b) FROM abcd` |
| `frameless.SelectTests` | `select('a,'b,'c,'d,'a,'c,'b, 'a) FROM abcd` |
| `frameless.SelectTests` | `select('a,'b,'c,'d,'a,'c,'b,'a,'c) FROM abcd` |
| `frameless.SelectTests` | `select('a,'b,'c,'d,'a,'c,'b,'a,'c, 'd) FROM abcd` |
| `frameless.SelectTests` | `select('a.b)` |
| `frameless.SelectTests` | `tests to cover problematic dataframe column names during projections` |
| `frameless.SelectTests` | `unary - on arithmetic` |
| `frameless.SelectTests` | `unary - on strings should not type check` |
| `frameless.SelfJoinTests` | `Do you want ambiguous self join? This is how you get ambiguous self join.` |
| `frameless.SelfJoinTests` | `colLeft and colRight are equivalent to col outside of joins` |
| `frameless.SelfJoinTests` | `colLeft and colRight are equivalent to col outside of joins - via files (codegen)` |
| `frameless.SelfJoinTests` | `self join with colLeft/colRight disambiguation` |
| `frameless.SelfJoinTests` | `self join with unambiguous expression` |
| `frameless.SelfJoinTests` | `trivial self join` |
| `frameless.SparkSessionTests` | `sparkSession` |
| `frameless.StorageLevelTests` | `storageLevel` |
| `frameless.TakeTests` | `take` |
| `frameless.ToJSONTests` | `toJSON` |
| `frameless.ToLocalIteratorTests` | `toLocalIterator` |
| `frameless.UnionTests` | `Align fields for case classes` |
| `frameless.UnionTests` | `Align fields for different number of columns` |
| `frameless.UnionTests` | `Union for simple data types` |
| `frameless.UnionTests` | `fail to compile on not aligned schema` |
| `frameless.WithColumnTest` | `append four columns` |
| `frameless.WithColumnTest` | `fail to compile on added column name` |
| `frameless.WithColumnTest` | `fail to compile on different column name` |
| `frameless.WithColumnTest` | `fail to compile on missing value` |
| `frameless.WithColumnTest` | `fail to compile on wrong typed column` |
| `frameless.WithColumnTest` | `update in place` |
| `frameless.WithColumnTupledTest` | `append five columns` |
| `frameless.WriteStreamTests` | `write csv` |
| `frameless.WriteStreamTests` | `write parquet` |
| `frameless.WriteTests` | `write csv` |
| `frameless.WriteTests` | `write parquet` |
| `frameless.forward.ForeachTests` | `foreach` |
| `frameless.forward.ForeachTests` | `foreachPartition` |
| `frameless.forward.HeadTests` | `headOption(), head(1), and head(4)` |
| `frameless.functions.AggregateFunctionsTests` | `approxCountDistinct` |
| `frameless.functions.AggregateFunctionsTests` | `avg` |
| `frameless.functions.AggregateFunctionsTests` | `collectList` |
| `frameless.functions.AggregateFunctionsTests` | `collectSet` |
| `frameless.functions.AggregateFunctionsTests` | `corr` |
| `frameless.functions.AggregateFunctionsTests` | `count` |
| `frameless.functions.AggregateFunctionsTests` | `count('a)` |
| `frameless.functions.AggregateFunctionsTests` | `countDistinct` |
| `frameless.functions.AggregateFunctionsTests` | `covar_pop` |
| `frameless.functions.AggregateFunctionsTests` | `covar_samp` |
| `frameless.functions.AggregateFunctionsTests` | `first` |
| `frameless.functions.AggregateFunctionsTests` | `kurtosis` |
| `frameless.functions.AggregateFunctionsTests` | `last` |
| `frameless.functions.AggregateFunctionsTests` | `lit` |
| `frameless.functions.AggregateFunctionsTests` | `litAggr` |
| `frameless.functions.AggregateFunctionsTests` | `max` |
| `frameless.functions.AggregateFunctionsTests` | `max with follow up multiplication` |
| `frameless.functions.AggregateFunctionsTests` | `min` |
| `frameless.functions.AggregateFunctionsTests` | `skewness` |
| `frameless.functions.AggregateFunctionsTests` | `stddev and variance` |
| `frameless.functions.AggregateFunctionsTests` | `stddev_pop` |
| `frameless.functions.AggregateFunctionsTests` | `stddev_samp` |
| `frameless.functions.AggregateFunctionsTests` | `sum` |
| `frameless.functions.AggregateFunctionsTests` | `sumDistinct` |
| `frameless.functions.NonAggregateFunctionsTests` | `Empty vararg tests` |
| `frameless.functions.NonAggregateFunctionsTests` | `abs` |
| `frameless.functions.NonAggregateFunctionsTests` | `abs big decimal` |
| `frameless.functions.NonAggregateFunctionsTests` | `acos` |
| `frameless.functions.NonAggregateFunctionsTests` | `arrayContains` |
| `frameless.functions.NonAggregateFunctionsTests` | `ascii` |
| `frameless.functions.NonAggregateFunctionsTests` | `asin` |
| `frameless.functions.NonAggregateFunctionsTests` | `atan` |
| `frameless.functions.NonAggregateFunctionsTests` | `atan2` |
| `frameless.functions.NonAggregateFunctionsTests` | `atan2LitLeft` |
| `frameless.functions.NonAggregateFunctionsTests` | `atan2LitRight` |
| `frameless.functions.NonAggregateFunctionsTests` | `base64` |
| `frameless.functions.NonAggregateFunctionsTests` | `bin` |
| `frameless.functions.NonAggregateFunctionsTests` | `bitwiseNOT` |
| `frameless.functions.NonAggregateFunctionsTests` | `bround` |
| `frameless.functions.NonAggregateFunctionsTests` | `bround big decimal` |
| `frameless.functions.NonAggregateFunctionsTests` | `bround big decimal with scale` |
| `frameless.functions.NonAggregateFunctionsTests` | `bround with scale` |
| `frameless.functions.NonAggregateFunctionsTests` | `ceil` |
| `frameless.functions.NonAggregateFunctionsTests` | `concat` |
| `frameless.functions.NonAggregateFunctionsTests` | `concat for TypedAggregate` |
| `frameless.functions.NonAggregateFunctionsTests` | `concat_ws` |
| `frameless.functions.NonAggregateFunctionsTests` | `concat_ws for TypedAggregate` |
| `frameless.functions.NonAggregateFunctionsTests` | `conv` |
| `frameless.functions.NonAggregateFunctionsTests` | `cos` |
| `frameless.functions.NonAggregateFunctionsTests` | `cosh` |
| `frameless.functions.NonAggregateFunctionsTests` | `crbt` |
| `frameless.functions.NonAggregateFunctionsTests` | `crc32` |
| `frameless.functions.NonAggregateFunctionsTests` | `dayofmonth` |
| `frameless.functions.NonAggregateFunctionsTests` | `dayofweek` |
| `frameless.functions.NonAggregateFunctionsTests` | `dayofyear` |
| `frameless.functions.NonAggregateFunctionsTests` | `degrees` |
| `frameless.functions.NonAggregateFunctionsTests` | `exp` |
| `frameless.functions.NonAggregateFunctionsTests` | `factorial` |
| `frameless.functions.NonAggregateFunctionsTests` | `floor` |
| `frameless.functions.NonAggregateFunctionsTests` | `hour` |
| `frameless.functions.NonAggregateFunctionsTests` | `hypot with double` |
| `frameless.functions.NonAggregateFunctionsTests` | `hypot with two columns` |
| `frameless.functions.NonAggregateFunctionsTests` | `inputFileName` |
| `frameless.functions.NonAggregateFunctionsTests` | `instr` |
| `frameless.functions.NonAggregateFunctionsTests` | `length` |
| `frameless.functions.NonAggregateFunctionsTests` | `levenshtein` |
| `frameless.functions.NonAggregateFunctionsTests` | `log` |
| `frameless.functions.NonAggregateFunctionsTests` | `log with base` |
| `frameless.functions.NonAggregateFunctionsTests` | `log10` |
| `frameless.functions.NonAggregateFunctionsTests` | `log1p` |
| `frameless.functions.NonAggregateFunctionsTests` | `log2` |
| `frameless.functions.NonAggregateFunctionsTests` | `lower` |
| `frameless.functions.NonAggregateFunctionsTests` | `lpad` |
| `frameless.functions.NonAggregateFunctionsTests` | `ltrim` |
| `frameless.functions.NonAggregateFunctionsTests` | `md5` |
| `frameless.functions.NonAggregateFunctionsTests` | `minute` |
| `frameless.functions.NonAggregateFunctionsTests` | `monotonic id` |
| `frameless.functions.NonAggregateFunctionsTests` | `month` |
| `frameless.functions.NonAggregateFunctionsTests` | `negate` |
| `frameless.functions.NonAggregateFunctionsTests` | `not` |
| `frameless.functions.NonAggregateFunctionsTests` | `pmod` |
| `frameless.functions.NonAggregateFunctionsTests` | `pow with double` |
| `frameless.functions.NonAggregateFunctionsTests` | `pow with two columns` |
| `frameless.functions.NonAggregateFunctionsTests` | `quarter` |
| `frameless.functions.NonAggregateFunctionsTests` | `regexp_replace` |
| `frameless.functions.NonAggregateFunctionsTests` | `reverse` |
| `frameless.functions.NonAggregateFunctionsTests` | `round` |
| `frameless.functions.NonAggregateFunctionsTests` | `round big decimal` |
| `frameless.functions.NonAggregateFunctionsTests` | `round big decimal with scale` |
| `frameless.functions.NonAggregateFunctionsTests` | `round with scale` |
| `frameless.functions.NonAggregateFunctionsTests` | `rpad` |
| `frameless.functions.NonAggregateFunctionsTests` | `rtrim` |
| `frameless.functions.NonAggregateFunctionsTests` | `second` |
| `frameless.functions.NonAggregateFunctionsTests` | `sha1` |
| `frameless.functions.NonAggregateFunctionsTests` | `sha2` |
| `frameless.functions.NonAggregateFunctionsTests` | `shiftLeft` |
| `frameless.functions.NonAggregateFunctionsTests` | `shiftRight` |
| `frameless.functions.NonAggregateFunctionsTests` | `shiftRightUnsigned` |
| `frameless.functions.NonAggregateFunctionsTests` | `signum` |
| `frameless.functions.NonAggregateFunctionsTests` | `sin` |
| `frameless.functions.NonAggregateFunctionsTests` | `sinh` |
| `frameless.functions.NonAggregateFunctionsTests` | `sqrt` |
| `frameless.functions.NonAggregateFunctionsTests` | `substring` |
| `frameless.functions.NonAggregateFunctionsTests` | `tan` |
| `frameless.functions.NonAggregateFunctionsTests` | `tanh` |
| `frameless.functions.NonAggregateFunctionsTests` | `trim` |
| `frameless.functions.NonAggregateFunctionsTests` | `unbase64` |
| `frameless.functions.NonAggregateFunctionsTests` | `upper` |
| `frameless.functions.NonAggregateFunctionsTests` | `weekofyear` |
| `frameless.functions.NonAggregateFunctionsTests` | `when` |
| `frameless.functions.NonAggregateFunctionsTests` | `year` |
| `frameless.functions.UdfTests` | `five argument udf` |
| `frameless.functions.UdfTests` | `four argument udf` |
| `frameless.functions.UdfTests` | `multiple one argument udf` |
| `frameless.functions.UdfTests` | `multiple two argument udf` |
| `frameless.functions.UdfTests` | `one argument udf` |
| `frameless.functions.UdfTests` | `three argument udf` |
| `frameless.functions.UdfTests` | `two argument udf` |
| `frameless.functions.UnaryFunctionsTest` | `size on Map` |
| `frameless.functions.UnaryFunctionsTest` | `size on array test` |
| `frameless.functions.UnaryFunctionsTest` | `size tests` |
| `frameless.functions.UnaryFunctionsTest` | `sort in ascending order` |
| `frameless.functions.UnaryFunctionsTest` | `sort in descending order` |
| `frameless.functions.UnaryFunctionsTest` | `sort on array test: ascending order` |
| `frameless.functions.UnaryFunctionsTest` | `sort on array test: descending order` |
| `frameless.ops.ColumnTypesTest` | `test summoning` |
| `frameless.ops.CubeTests` | `cube('a).agg(count())` |
| `frameless.ops.CubeTests` | `cube('a).agg(sum('b)` |
| `frameless.ops.CubeTests` | `cube('a).agg(sum('b), sum('c)) to cube('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))` |
| `frameless.ops.CubeTests` | `cube('a).flatMapGroups(('a, toVector(('a, 'b))` |
| `frameless.ops.CubeTests` | `cube('a).mapGroups('a, sum('b))` |
| `frameless.ops.CubeTests` | `cube('a).mapGroups(('a, toVector(('a, 'b))` |
| `frameless.ops.CubeTests` | `cube('a, 'b).agg(count())` |
| `frameless.ops.CubeTests` | `cube('a, 'b).agg(sum('c)) to cube('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))` |
| `frameless.ops.CubeTests` | `cube('a, 'b).agg(sum('c), sum('d))` |
| `frameless.ops.CubeTests` | `cube('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))` |
| `frameless.ops.CubeTests` | `cube('a, 'b).mapGroups('a, 'b, sum('c))` |
| `frameless.ops.CubeTests` | `cubeMany('a).agg(sum('b))` |
| `frameless.ops.PivotTest` | `Pivot on Boolean` |
| `frameless.ops.PivotTest` | `Pivot with cube on Boolean` |
| `frameless.ops.PivotTest` | `Pivot with cube on two columns, pivot on Long` |
| `frameless.ops.PivotTest` | `Pivot with groupBy on two columns, pivot on Long` |
| `frameless.ops.PivotTest` | `Pivot with rollup on Boolean` |
| `frameless.ops.PivotTest` | `Pivot with rollup on two columns, pivot on Long` |
| `frameless.ops.PivotTest` | `X4[Boolean, String, Int, Boolean] pivot on String` |
| `frameless.ops.RepeatTest` | `ill typed` |
| `frameless.ops.RepeatTest` | `summoning with implicitly` |
| `frameless.ops.RollupTests` | `rollup('a).agg(count())` |
| `frameless.ops.RollupTests` | `rollup('a).agg(sum('b)` |
| `frameless.ops.RollupTests` | `rollup('a).agg(sum('b), sum('c)) to rollup('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))` |
| `frameless.ops.RollupTests` | `rollup('a).flatMapGroups(('a, toVector(('a, 'b))` |
| `frameless.ops.RollupTests` | `rollup('a).mapGroups('a, sum('b))` |
| `frameless.ops.RollupTests` | `rollup('a).mapGroups(('a, toVector(('a, 'b))` |
| `frameless.ops.RollupTests` | `rollup('a, 'b).agg(count())` |
| `frameless.ops.RollupTests` | `rollup('a, 'b).agg(sum('c)) to rollup('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))` |
| `frameless.ops.RollupTests` | `rollup('a, 'b).agg(sum('c), sum('d))` |
| `frameless.ops.RollupTests` | `rollup('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))` |
| `frameless.ops.RollupTests` | `rollup('a, 'b).mapGroups('a, 'b, sum('c))` |
| `frameless.ops.RollupTests` | `rollupMany('a).agg(sum('b))` |
| `frameless.ops.SmartProjectTest` | `X3U to X1,X2,X3 projections` |
| `frameless.ops.SmartProjectTest` | `X4 to X1,X2,X3,X4 projections` |
| `frameless.ops.SmartProjectTest` | `project Foo to Bar` |
| `frameless.ops.SmartProjectTest` | `project to InvalidFooProjection should not type check` |
| `frameless.ops.deserialized.FilterTests` | `filter` |
| `frameless.ops.deserialized.FlatMapTests` | `flatMap` |
| `frameless.ops.deserialized.MapPartitionsTests` | `mapPartitions` |
| `frameless.ops.deserialized.MapTests` | `map` |
| `frameless.ops.deserialized.ReduceTests` | `reduce Int` |
| `frameless.ops.deserialized.ReduceTests` | `reduce String` |
| `frameless.sql.rules.FramelessLitPushDownTests` | `java.sql.Timestamp push-down` |
| `frameless.sql.rules.FramelessLitPushDownTests` | `java.time.Instant push-down` |
| `frameless.sql.rules.FramelessLitPushDownTests` | `struct push-down` |
| `frameless.syntax.FramelessSyntaxTests` | `dataset typed - toTyped` |
| `frameless.syntax.FramelessSyntaxTests` | `frameless typed column and aggregate` |

### Module: `ml-spark40` (28 new tests)

| Test Class | Test Name |
|------------|----------|
| `frameless.ml.TypedEncoderInstancesTests` | `Matrix encoding is injective using collect()` |
| `frameless.ml.TypedEncoderInstancesTests` | `Vector encoding is injective using collect()` |
| `frameless.ml.TypedEncoderInstancesTests` | `Vector is encoded as VectorUDT and thus can be run in a Spark ML model` |
| `frameless.ml.classification.ClassificationIntegrationTests` | `predict field3 from field1 and field2 using a RandomForestClassifier` |
| `frameless.ml.classification.TypedRandomForestClassifierTests` | `create() compiles only with correct inputs` |
| `frameless.ml.classification.TypedRandomForestClassifierTests` | `fit() returns a correct TypedTransformer` |
| `frameless.ml.classification.TypedRandomForestClassifierTests` | `param setting is retained` |
| `frameless.ml.clustering.BisectingKMeansTests` | `fit() returns a correct TypedTransformer` |
| `frameless.ml.clustering.BisectingKMeansTests` | `param setting is retained` |
| `frameless.ml.clustering.ClusteringIntegrationTests` | `predict field2 from field1 using a K-means clustering` |
| `frameless.ml.clustering.ClusteringIntegrationTests` | `predict field2 from field1 using a bisecting K-means clustering` |
| `frameless.ml.clustering.KMeansTests` | `fit() returns a correct TypedTransformer` |
| `frameless.ml.clustering.KMeansTests` | `param setting is retained` |
| `frameless.ml.feature.TypedIndexToStringTests` | `.transform() correctly transform an input dataset` |
| `frameless.ml.feature.TypedIndexToStringTests` | `create() compiles only with correct inputs` |
| `frameless.ml.feature.TypedStringIndexerTests` | `.fit() returns a correct TypedTransformer` |
| `frameless.ml.feature.TypedStringIndexerTests` | `create() compiles only with correct inputs` |
| `frameless.ml.feature.TypedStringIndexerTests` | `param setting is retained` |
| `frameless.ml.feature.TypedVectorAssemblerTests` | `.transform() returns a correct TypedTransformer` |
| `frameless.ml.feature.TypedVectorAssemblerTests` | `create() compiles only with correct inputs` |
| `frameless.ml.regression.RegressionIntegrationTests` | `predict field3 from field1 and field2 using a RandomForestRegressor` |
| `frameless.ml.regression.TypedLinearRegressionTests` | `TypedLinearRegressor should fit straight line` |
| `frameless.ml.regression.TypedLinearRegressionTests` | `create() compiles only with correct inputs` |
| `frameless.ml.regression.TypedLinearRegressionTests` | `fit() returns a correct TypedTransformer` |
| `frameless.ml.regression.TypedLinearRegressionTests` | `param setting is retained` |
| `frameless.ml.regression.TypedRandomForestRegressorTests` | `create() compiles only with correct inputs` |
| `frameless.ml.regression.TypedRandomForestRegressorTests` | `fit() returns a correct TypedTransformer` |
| `frameless.ml.regression.TypedRandomForestRegressorTests` | `param setting is retained` |

### Module: `refined-spark40` (3 new tests)

| Test Class | Test Name |
|------------|----------|
| `frameless.RefinedFieldEncoderTests` | `Encode a bare refined type` |
| `frameless.RefinedFieldEncoderTests` | `Encode case class with a refined field` |
| `frameless.RefinedFieldEncoderTests` | `Encode case class with a refined optional field` |

---

## Tests Only in Legacy Project (Removed/Missing Tests)

*No tests removed from legacy project.*

---

## Module-Level Test Count Comparison

| Module | Tests in Current | Tests in Legacy | Difference |
|--------|-----------------|-----------------|------------|
| `cats-spark40` | 8 | 0 | +8 |
| `dataset` | 0 | 3 | -3 |
| `dataset-spark40` | 414 | 0 | +414 |
| `frameless` | 0 | 1 | -1 |
| `ml-spark40` | 28 | 0 | +28 |
| `refined-spark40` | 3 | 0 | +3 |

