# Missing Tests Comparison

**Analysis of test differences between Current and Legacy projects**

---

## Summary

- **Tests in Current but missing in Legacy:** 449
- **Tests in Legacy but missing in Current:** 0
- **Test suites only in Current:** 82
- **Test suites only in Legacy:** 0

## All Missing Tests (Single Table)

| Status | Module | Test Suite | Test Name |
|--------|--------|------------|----------|
| ✅ **New in Current** | `cats` | `frameless.cats.Test` | inner pairwise monoid |
| ✅ **New in Current** | `cats` | `frameless.cats.Test` | pair rdd numeric commutative semigroup example |
| ✅ **New in Current** | `cats` | `frameless.cats.Test` | rdd of SortedMap[Int,Int] commutative monoid |
| ✅ **New in Current** | `cats` | `frameless.cats.Test` | rdd simple numeric commutative semigroup |
| ✅ **New in Current** | `cats` | `frameless.cats.Test` | rdd tuple commutative semigroup example |
| ✅ **New in Current** | `cats` | `frameless.cats.FramelessSyntaxTests` | dataset typed - toTyped |
| ✅ **New in Current** | `cats` | `frameless.cats.FramelessSyntaxTests` | properties can be read back |
| ✅ **New in Current** | `dataset` | `frameless.AsTests` | as[X2[A, B]] |
| ✅ **New in Current** | `dataset` | `frameless.AsTests` | as[X2[X2[A, B], C] |
| ✅ **New in Current** | `dataset` | `frameless.BitwiseTests` | bitwiseAND |
| ✅ **New in Current** | `dataset` | `frameless.BitwiseTests` | bitwiseOR |
| ✅ **New in Current** | `dataset` | `frameless.BitwiseTests` | bitwiseXOR |
| ✅ **New in Current** | `dataset` | `frameless.CastTests` | cast |
| ✅ **New in Current** | `dataset` | `frameless.CheckpointTests` | checkpoint |
| ✅ **New in Current** | `dataset` | `frameless.ColTests` | col |
| ✅ **New in Current** | `dataset` | `frameless.ColTests` | colMany |
| ✅ **New in Current** | `dataset` | `frameless.ColTests` | select colMany |
| ✅ **New in Current** | `dataset` | `frameless.CollectTests` | collect() |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | Consistency with Spark internal date/time representation |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | asCol |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | asCol single column TypedDatasets |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | asCol with numeric operators |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | between |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | boolean and / or |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | col through lambda |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | contains |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | endsWith |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | field |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | field compiles only for valid field |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | getOrElse |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | like |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | opt |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | opt compiles only for columns of type Option[_] |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | reference Value class so can join on |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | rlike |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | select('a < 'b, 'a <= 'b, 'a > 'b, 'a >= 'b) |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | startsWith |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | substr |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | toString |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | unary_! |
| ✅ **New in Current** | `dataset` | `frameless.ColumnTests` | unary_! with non-boolean columns should not compile |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col((x: MyClass1) => x.a |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col((x: MyClass1) => x.c.e.f |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col((x: MyClass1) => x.toString.size) does not compile |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(_.a) |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(_.a.toString) does not compile |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(_.a.toString.size) does not compile |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(_.c.d) |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(_.c.d) as int does not compile (is long) |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(_.c.e.f) |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(_.g.h does not compile |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(x => java.lang.Math.abs(x.a)) does not compile |
| ✅ **New in Current** | `dataset` | `frameless.ColumnViaLambdaTests` | col(x => x.a |
| ✅ **New in Current** | `dataset` | `frameless.ColumnsTests` | columns |
| ✅ **New in Current** | `dataset` | `frameless.CountTests` | count |
| ✅ **New in Current** | `dataset` | `frameless.CreateTests` | Map fields (scala.Predef.Map / scala.collection.immutable.Map) |
| ✅ **New in Current** | `dataset` | `frameless.CreateTests` | array fields |
| ✅ **New in Current** | `dataset` | `frameless.CreateTests` | creation using X4 derived DataFrames |
| ✅ **New in Current** | `dataset` | `frameless.CreateTests` | dataset with different column order |
| ✅ **New in Current** | `dataset` | `frameless.CreateTests` | list fields |
| ✅ **New in Current** | `dataset` | `frameless.CreateTests` | maps with Option keys should not resolve the TypedEncoder |
| ✅ **New in Current** | `dataset` | `frameless.CreateTests` | not aligned columns should throw an exception |
| ✅ **New in Current** | `dataset` | `frameless.CreateTests` | vector fields |
| ✅ **New in Current** | `dataset` | `frameless.DistinctTests` | distinct |
| ✅ **New in Current** | `dataset` | `frameless.DropTest` | drop four columns |
| ✅ **New in Current** | `dataset` | `frameless.DropTest` | fail to compile on added column name |
| ✅ **New in Current** | `dataset` | `frameless.DropTest` | fail to compile on different column name |
| ✅ **New in Current** | `dataset` | `frameless.DropTest` | fail to compile on missing value |
| ✅ **New in Current** | `dataset` | `frameless.DropTest` | remove column in the middle |
| ✅ **New in Current** | `dataset` | `frameless.DropTupledTest` | drop first column |
| ✅ **New in Current** | `dataset` | `frameless.DropTupledTest` | drop five columns |
| ✅ **New in Current** | `dataset` | `frameless.DropTupledTest` | drop last column |
| ✅ **New in Current** | `dataset` | `frameless.DropTupledTest` | drop middle column |
| ✅ **New in Current** | `dataset` | `frameless.EncoderTests` | It should encode deeply nested collections |
| ✅ **New in Current** | `dataset` | `frameless.EncoderTests` | It should encode java.time.Duration |
| ✅ **New in Current** | `dataset` | `frameless.EncoderTests` | It should encode java.time.Instant |
| ✅ **New in Current** | `dataset` | `frameless.EncoderTests` | It should encode java.time.Period |
| ✅ **New in Current** | `dataset` | `frameless.ExceptTests` | except |
| ✅ **New in Current** | `dataset` | `frameless.ExplodeTests` | explode on arrays |
| ✅ **New in Current** | `dataset` | `frameless.ExplodeTests` | explode on maps |
| ✅ **New in Current** | `dataset` | `frameless.ExplodeTests` | explode on maps making sure no key / value naming collision happens |
| ✅ **New in Current** | `dataset` | `frameless.ExplodeTests` | explode on maps preserving other columns |
| ✅ **New in Current** | `dataset` | `frameless.ExplodeTests` | explode on vectors/list/seq |
| ✅ **New in Current** | `dataset` | `frameless.ExplodeTests` | simple explode test |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | Option content filter |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | Option equality/inequality for columns |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | Option equality/inequality for lit |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | filter with arithmetic expressions: addition |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | filter with arithmetic expressions: multiplication |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | filter with isin values |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | filter with values (not columns): addition |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | filter('a =!= 'b |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | filter('a =!= 'b) |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | filter('a =!= lit(b)) |
| ✅ **New in Current** | `dataset` | `frameless.FilterTests` | filter('a == lit(b)) |
| ✅ **New in Current** | `dataset` | `frameless.FirstTests` | first |
| ✅ **New in Current** | `dataset` | `frameless.FirstTests` | first on empty dataset should return None |
| ✅ **New in Current** | `dataset` | `frameless.FlattenTests` | different Optional types |
| ✅ **New in Current** | `dataset` | `frameless.FlattenTests` | simple flatten test |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | agg(sum('a)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | agg(sum('a), sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | agg(sum('a), sum('b), min('c), max('d)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | agg(sum('a), sum('b), sum('c)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a).agg(sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a).agg(sum('b), sum('c)) to groupBy('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a).flatMapGroups(('a, toVector(('a, 'b)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a).mapGroups('a, sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a).mapGroups(('a, toVector(('a, 'b)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a, 'b).agg(sum('c)) to groupBy('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a, 'b).agg(sum('c), sum('d)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupBy('a, 'b).mapGroups('a, 'b, sum('c)) |
| ✅ **New in Current** | `dataset` | `frameless.GroupByTests` | groupByMany('a).agg(sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | Derive encoder for ADT with abstract class as the base type |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | Derive encoder for phantom type |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | Derive encoder for type with data constructors defined as parameterless case classes |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | Derive encoder for type with data constructors defined in the companion object |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | Injection based encoders |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | Resolve ambiguity by importing usingDerivation |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | Resolve ambiguity by importing usingInjection |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | Resolve missing implicit by deriving Injection instance |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | TypedEncoder[Employee] implicit is missing |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | TypedEncoder[Maybe] cannot be derived |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | TypedEncoder[Person] is ambiguous |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | apply method of derived Injection instance produces the correct string |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | invert method of derived Injection instance produces the correct value |
| ✅ **New in Current** | `dataset` | `frameless.InjectionTests` | invert method of derived Injection instance should throw exception if string does not match data constructor names |
| ✅ **New in Current** | `dataset` | `frameless.InputFilesTests` | inputFiles |
| ✅ **New in Current** | `dataset` | `frameless.IntersectTests` | intersect |
| ✅ **New in Current** | `dataset` | `frameless.IsLocalTests` | isLocal |
| ✅ **New in Current** | `dataset` | `frameless.IsStreamingTests` | isStreaming |
| ✅ **New in Current** | `dataset` | `frameless.JobTests` | flatMap associativity |
| ✅ **New in Current** | `dataset` | `frameless.JobTests` | flatMap left identity |
| ✅ **New in Current** | `dataset` | `frameless.JobTests` | flatMap right identity |
| ✅ **New in Current** | `dataset` | `frameless.JobTests` | map composition |
| ✅ **New in Current** | `dataset` | `frameless.JobTests` | map identity |
| ✅ **New in Current** | `dataset` | `frameless.JobTests` | properties read back |
| ✅ **New in Current** | `dataset` | `frameless.JoinTests` | ab.joinCross(ac) |
| ✅ **New in Current** | `dataset` | `frameless.JoinTests` | ab.joinFull(ac)(ab.a == ac.a) |
| ✅ **New in Current** | `dataset` | `frameless.JoinTests` | ab.joinInner(ac)(ab.a == ac.a) |
| ✅ **New in Current** | `dataset` | `frameless.JoinTests` | ab.joinLeft(ac)(ab.a == ac.a) |
| ✅ **New in Current** | `dataset` | `frameless.JoinTests` | ab.joinLeftAnti(ac)(ab.a == ac.a) |
| ✅ **New in Current** | `dataset` | `frameless.JoinTests` | ab.joinLeftSemi(ac)(ab.a == ac.a) |
| ✅ **New in Current** | `dataset` | `frameless.JoinTests` | ab.joinRight(ac)(ab.a == ac.a) |
| ✅ **New in Current** | `dataset` | `frameless.LimitTests` | limit |
| ✅ **New in Current** | `dataset` | `frameless.LitTests` | #205: comparing literals encoded using Injection |
| ✅ **New in Current** | `dataset` | `frameless.LitTests` | select(lit(...)) |
| ✅ **New in Current** | `dataset` | `frameless.LitTests` | support optional value class |
| ✅ **New in Current** | `dataset` | `frameless.LitTests` | support value class |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | a mod lit(b) |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | divide |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | divide BigDecimals |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | isNaN |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | isNaN with non-nan types should not compile |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | minus |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | mod |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | multiply |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | multiply BigDecimal |
| ✅ **New in Current** | `dataset` | `frameless.NumericTests` | plus |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | derives a CatalystOrdered for case classes when all fields are comparable |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | derives a CatalystOrdered for tuples when all fields are comparable |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | fail when selected column is not sortable |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | fails to compile when one of the field isn't comparable |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | single column non nullable orderBy |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | single column non nullable partition sorting |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | sort support for mixed default and explicit ordering |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | three columns non nullable orderBy |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | three columns non nullable partition sorting |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | two columns non nullable orderBy |
| ✅ **New in Current** | `dataset` | `frameless.OrderByTests` | two columns non nullable partition sorting |
| ✅ **New in Current** | `dataset` | `frameless.QueryExecutionTests` | queryExecution |
| ✅ **New in Current** | `dataset` | `frameless.RandomSplitTests` | randomSplit(weight, seed) |
| ✅ **New in Current** | `dataset` | `frameless.RandomSplitTests` | randomSplitAsList(weight, seed) |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Case class with Map & Value class |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Case class with simple Map |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Case class with value class as optional field |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Case class with value class field |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Deeply nested optional values have correct deserialization |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Dropping fields |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Empty nested record value becomes none on deserialization |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Empty nested record value becomes null on serialization |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Encode array of Value class |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Encode binary array |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Encode case class with Value class |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Encode case class with simple Seq |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Encode simple array |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Nesting with Seq |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Nesting with Set |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Representation skips units |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Scalar value class |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Serialization skips units |
| ✅ **New in Current** | `dataset` | `frameless.RecordEncoderTests` | Unable to encode products made from units only |
| ✅ **New in Current** | `dataset` | `frameless.SQLContextTests` | sqlContext |
| ✅ **New in Current** | `dataset` | `frameless.SchemaTests` | schema of groupBy('a).agg(sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.SchemaTests` | schema of select(lit(1L)) |
| ✅ **New in Current** | `dataset` | `frameless.SchemaTests` | schema of select(lit(1L), lit(2L)).as[X2[Long, Long]] |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select with aggregation operations is not supported |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select with column expression addition |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select with column expression division |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select with column expression multiplication |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select with column expression subtraction |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a, 'b) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a, 'b, 'c) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a,'b,'c,'d) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a,'b,'c,'d,'a) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a,'b,'c,'d,'a, 'c) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a,'b,'c,'d,'a,'c,'b) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a,'b,'c,'d,'a,'c,'b, 'a) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a,'b,'c,'d,'a,'c,'b,'a,'c) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a,'b,'c,'d,'a,'c,'b,'a,'c, 'd) FROM abcd |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | select('a.b) |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | tests to cover problematic dataframe column names during projections |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | unary - on arithmetic |
| ✅ **New in Current** | `dataset` | `frameless.SelectTests` | unary - on strings should not type check |
| ✅ **New in Current** | `dataset` | `frameless.SelfJoinTests` | Do you want ambiguous self join? This is how you get ambiguous self join. |
| ✅ **New in Current** | `dataset` | `frameless.SelfJoinTests` | colLeft and colRight are equivalent to col outside of joins |
| ✅ **New in Current** | `dataset` | `frameless.SelfJoinTests` | colLeft and colRight are equivalent to col outside of joins - via files (codegen) |
| ✅ **New in Current** | `dataset` | `frameless.SelfJoinTests` | self join with colLeft/colRight disambiguation |
| ✅ **New in Current** | `dataset` | `frameless.SelfJoinTests` | self join with unambiguous expression |
| ✅ **New in Current** | `dataset` | `frameless.SelfJoinTests` | trivial self join |
| ✅ **New in Current** | `dataset` | `frameless.SparkSessionTests` | sparkSession |
| ✅ **New in Current** | `dataset` | `frameless.StorageLevelTests` | storageLevel |
| ✅ **New in Current** | `dataset` | `frameless.TakeTests` | take |
| ✅ **New in Current** | `dataset` | `frameless.ToJSONTests` | toJSON |
| ✅ **New in Current** | `dataset` | `frameless.ToLocalIteratorTests` | toLocalIterator |
| ✅ **New in Current** | `dataset` | `frameless.UnionTests` | Align fields for case classes |
| ✅ **New in Current** | `dataset` | `frameless.UnionTests` | Align fields for different number of columns |
| ✅ **New in Current** | `dataset` | `frameless.UnionTests` | Union for simple data types |
| ✅ **New in Current** | `dataset` | `frameless.UnionTests` | fail to compile on not aligned schema |
| ✅ **New in Current** | `dataset` | `frameless.WithColumnTest` | append four columns |
| ✅ **New in Current** | `dataset` | `frameless.WithColumnTest` | fail to compile on added column name |
| ✅ **New in Current** | `dataset` | `frameless.WithColumnTest` | fail to compile on different column name |
| ✅ **New in Current** | `dataset` | `frameless.WithColumnTest` | fail to compile on missing value |
| ✅ **New in Current** | `dataset` | `frameless.WithColumnTest` | fail to compile on wrong typed column |
| ✅ **New in Current** | `dataset` | `frameless.WithColumnTest` | update in place |
| ✅ **New in Current** | `dataset` | `frameless.WithColumnTupledTest` | append five columns |
| ✅ **New in Current** | `dataset` | `frameless.WriteStreamTests` | write csv |
| ✅ **New in Current** | `dataset` | `frameless.WriteStreamTests` | write parquet |
| ✅ **New in Current** | `dataset` | `frameless.WriteTests` | write csv |
| ✅ **New in Current** | `dataset` | `frameless.WriteTests` | write parquet |
| ✅ **New in Current** | `dataset` | `frameless.forward.ForeachTests` | foreach |
| ✅ **New in Current** | `dataset` | `frameless.forward.ForeachTests` | foreachPartition |
| ✅ **New in Current** | `dataset` | `frameless.forward.HeadTests` | headOption(), head(1), and head(4) |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | approxCountDistinct |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | avg |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | collectList |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | collectSet |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | corr |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | count |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | count('a) |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | countDistinct |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | covar_pop |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | covar_samp |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | first |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | kurtosis |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | last |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | lit |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | litAggr |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | max |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | max with follow up multiplication |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | min |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | skewness |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | stddev and variance |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | stddev_pop |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | stddev_samp |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | sum |
| ✅ **New in Current** | `dataset` | `frameless.functions.AggregateFunctionsTests` | sumDistinct |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | Empty vararg tests |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | abs |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | abs big decimal |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | acos |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | arrayContains |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | ascii |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | asin |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | atan |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | atan2 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | atan2LitLeft |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | atan2LitRight |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | base64 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | bin |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | bitwiseNOT |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | bround |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | bround big decimal |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | bround big decimal with scale |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | bround with scale |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | ceil |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | concat |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | concat for TypedAggregate |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | concat_ws |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | concat_ws for TypedAggregate |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | conv |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | cos |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | cosh |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | crbt |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | crc32 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | dayofmonth |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | dayofweek |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | dayofyear |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | degrees |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | exp |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | factorial |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | floor |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | hour |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | hypot with double |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | hypot with two columns |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | inputFileName |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | instr |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | length |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | levenshtein |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | log |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | log with base |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | log10 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | log1p |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | log2 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | lower |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | lpad |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | ltrim |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | md5 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | minute |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | monotonic id |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | month |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | negate |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | not |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | pmod |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | pow with double |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | pow with two columns |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | quarter |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | regexp_replace |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | reverse |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | round |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | round big decimal |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | round big decimal with scale |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | round with scale |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | rpad |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | rtrim |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | second |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | sha1 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | sha2 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | shiftLeft |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | shiftRight |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | shiftRightUnsigned |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | signum |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | sin |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | sinh |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | sqrt |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | substring |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | tan |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | tanh |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | trim |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | unbase64 |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | upper |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | weekofyear |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | when |
| ✅ **New in Current** | `dataset` | `frameless.functions.NonAggregateFunctionsTests` | year |
| ✅ **New in Current** | `dataset` | `frameless.functions.UdfTests` | five argument udf |
| ✅ **New in Current** | `dataset` | `frameless.functions.UdfTests` | four argument udf |
| ✅ **New in Current** | `dataset` | `frameless.functions.UdfTests` | multiple one argument udf |
| ✅ **New in Current** | `dataset` | `frameless.functions.UdfTests` | multiple two argument udf |
| ✅ **New in Current** | `dataset` | `frameless.functions.UdfTests` | one argument udf |
| ✅ **New in Current** | `dataset` | `frameless.functions.UdfTests` | three argument udf |
| ✅ **New in Current** | `dataset` | `frameless.functions.UdfTests` | two argument udf |
| ✅ **New in Current** | `dataset` | `frameless.functions.UnaryFunctionsTest` | size on Map |
| ✅ **New in Current** | `dataset` | `frameless.functions.UnaryFunctionsTest` | size on array test |
| ✅ **New in Current** | `dataset` | `frameless.functions.UnaryFunctionsTest` | size tests |
| ✅ **New in Current** | `dataset` | `frameless.functions.UnaryFunctionsTest` | sort in ascending order |
| ✅ **New in Current** | `dataset` | `frameless.functions.UnaryFunctionsTest` | sort in descending order |
| ✅ **New in Current** | `dataset` | `frameless.functions.UnaryFunctionsTest` | sort on array test: ascending order |
| ✅ **New in Current** | `dataset` | `frameless.functions.UnaryFunctionsTest` | sort on array test: descending order |
| ✅ **New in Current** | `dataset` | `frameless.ops.ColumnTypesTest` | test summoning |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a).agg(count()) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a).agg(sum('b) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a).agg(sum('b), sum('c)) to cube('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a).flatMapGroups(('a, toVector(('a, 'b)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a).mapGroups('a, sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a).mapGroups(('a, toVector(('a, 'b)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a, 'b).agg(count()) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a, 'b).agg(sum('c)) to cube('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a, 'b).agg(sum('c), sum('d)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cube('a, 'b).mapGroups('a, 'b, sum('c)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.CubeTests` | cubeMany('a).agg(sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.PivotTest` | Pivot on Boolean |
| ✅ **New in Current** | `dataset` | `frameless.ops.PivotTest` | Pivot with cube on Boolean |
| ✅ **New in Current** | `dataset` | `frameless.ops.PivotTest` | Pivot with cube on two columns, pivot on Long |
| ✅ **New in Current** | `dataset` | `frameless.ops.PivotTest` | Pivot with groupBy on two columns, pivot on Long |
| ✅ **New in Current** | `dataset` | `frameless.ops.PivotTest` | Pivot with rollup on Boolean |
| ✅ **New in Current** | `dataset` | `frameless.ops.PivotTest` | Pivot with rollup on two columns, pivot on Long |
| ✅ **New in Current** | `dataset` | `frameless.ops.PivotTest` | X4[Boolean, String, Int, Boolean] pivot on String |
| ✅ **New in Current** | `dataset` | `frameless.ops.RepeatTest` | ill typed |
| ✅ **New in Current** | `dataset` | `frameless.ops.RepeatTest` | summoning with implicitly |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a).agg(count()) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a).agg(sum('b) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a).agg(sum('b), sum('c)) to rollup('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a).flatMapGroups(('a, toVector(('a, 'b)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a).mapGroups('a, sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a).mapGroups(('a, toVector(('a, 'b)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a, 'b).agg(count()) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a, 'b).agg(sum('c)) to rollup('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a, 'b).agg(sum('c), sum('d)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollup('a, 'b).mapGroups('a, 'b, sum('c)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.RollupTests` | rollupMany('a).agg(sum('b)) |
| ✅ **New in Current** | `dataset` | `frameless.ops.SmartProjectTest` | X3U to X1,X2,X3 projections |
| ✅ **New in Current** | `dataset` | `frameless.ops.SmartProjectTest` | X4 to X1,X2,X3,X4 projections |
| ✅ **New in Current** | `dataset` | `frameless.ops.SmartProjectTest` | project Foo to Bar |
| ✅ **New in Current** | `dataset` | `frameless.ops.SmartProjectTest` | project to InvalidFooProjection should not type check |
| ✅ **New in Current** | `dataset` | `frameless.ops.deserialized.FilterTests` | filter |
| ✅ **New in Current** | `dataset` | `frameless.ops.deserialized.FlatMapTests` | flatMap |
| ✅ **New in Current** | `dataset` | `frameless.ops.deserialized.MapPartitionsTests` | mapPartitions |
| ✅ **New in Current** | `dataset` | `frameless.ops.deserialized.MapTests` | map |
| ✅ **New in Current** | `dataset` | `frameless.ops.deserialized.ReduceTests` | reduce Int |
| ✅ **New in Current** | `dataset` | `frameless.ops.deserialized.ReduceTests` | reduce String |
| ✅ **New in Current** | `dataset` | `frameless.sql.rules.FramelessLitPushDownTests` | java.sql.Timestamp push-down |
| ✅ **New in Current** | `dataset` | `frameless.sql.rules.FramelessLitPushDownTests` | java.time.Instant push-down |
| ✅ **New in Current** | `dataset` | `frameless.sql.rules.FramelessLitPushDownTests` | struct push-down |
| ✅ **New in Current** | `dataset` | `frameless.syntax.FramelessSyntaxTests` | dataset typed - toTyped |
| ✅ **New in Current** | `dataset` | `frameless.syntax.FramelessSyntaxTests` | frameless typed column and aggregate |
| ✅ **New in Current** | `ml` | `frameless.ml.TypedEncoderInstancesTests` | Matrix encoding is injective using collect() |
| ✅ **New in Current** | `ml` | `frameless.ml.TypedEncoderInstancesTests` | Vector encoding is injective using collect() |
| ✅ **New in Current** | `ml` | `frameless.ml.TypedEncoderInstancesTests` | Vector is encoded as VectorUDT and thus can be run in a Spark ML model |
| ✅ **New in Current** | `ml` | `frameless.ml.classification.ClassificationIntegrationTests` | predict field3 from field1 and field2 using a RandomForestClassifier |
| ✅ **New in Current** | `ml` | `frameless.ml.classification.TypedRandomForestClassifierTests` | create() compiles only with correct inputs |
| ✅ **New in Current** | `ml` | `frameless.ml.classification.TypedRandomForestClassifierTests` | fit() returns a correct TypedTransformer |
| ✅ **New in Current** | `ml` | `frameless.ml.classification.TypedRandomForestClassifierTests` | param setting is retained |
| ✅ **New in Current** | `ml` | `frameless.ml.clustering.BisectingKMeansTests` | fit() returns a correct TypedTransformer |
| ✅ **New in Current** | `ml` | `frameless.ml.clustering.BisectingKMeansTests` | param setting is retained |
| ✅ **New in Current** | `ml` | `frameless.ml.clustering.ClusteringIntegrationTests` | predict field2 from field1 using a K-means clustering |
| ✅ **New in Current** | `ml` | `frameless.ml.clustering.ClusteringIntegrationTests` | predict field2 from field1 using a bisecting K-means clustering |
| ✅ **New in Current** | `ml` | `frameless.ml.clustering.KMeansTests` | fit() returns a correct TypedTransformer |
| ✅ **New in Current** | `ml` | `frameless.ml.clustering.KMeansTests` | param setting is retained |
| ✅ **New in Current** | `ml` | `frameless.ml.feature.TypedIndexToStringTests` | .transform() correctly transform an input dataset |
| ✅ **New in Current** | `ml` | `frameless.ml.feature.TypedIndexToStringTests` | create() compiles only with correct inputs |
| ✅ **New in Current** | `ml` | `frameless.ml.feature.TypedStringIndexerTests` | .fit() returns a correct TypedTransformer |
| ✅ **New in Current** | `ml` | `frameless.ml.feature.TypedStringIndexerTests` | create() compiles only with correct inputs |
| ✅ **New in Current** | `ml` | `frameless.ml.feature.TypedStringIndexerTests` | param setting is retained |
| ✅ **New in Current** | `ml` | `frameless.ml.feature.TypedVectorAssemblerTests` | .transform() returns a correct TypedTransformer |
| ✅ **New in Current** | `ml` | `frameless.ml.feature.TypedVectorAssemblerTests` | create() compiles only with correct inputs |
| ✅ **New in Current** | `ml` | `frameless.ml.regression.RegressionIntegrationTests` | predict field3 from field1 and field2 using a RandomForestRegressor |
| ✅ **New in Current** | `ml` | `frameless.ml.regression.TypedLinearRegressionTests` | TypedLinearRegressor should fit straight line |
| ✅ **New in Current** | `ml` | `frameless.ml.regression.TypedLinearRegressionTests` | create() compiles only with correct inputs |
| ✅ **New in Current** | `ml` | `frameless.ml.regression.TypedLinearRegressionTests` | fit() returns a correct TypedTransformer |
| ✅ **New in Current** | `ml` | `frameless.ml.regression.TypedLinearRegressionTests` | param setting is retained |
| ✅ **New in Current** | `ml` | `frameless.ml.regression.TypedRandomForestRegressorTests` | create() compiles only with correct inputs |
| ✅ **New in Current** | `ml` | `frameless.ml.regression.TypedRandomForestRegressorTests` | fit() returns a correct TypedTransformer |
| ✅ **New in Current** | `ml` | `frameless.ml.regression.TypedRandomForestRegressorTests` | param setting is retained |
| ✅ **New in Current** | `refined` | `frameless.RefinedFieldEncoderTests` | Encode a bare refined type |
| ✅ **New in Current** | `refined` | `frameless.RefinedFieldEncoderTests` | Encode case class with a refined field |
| ✅ **New in Current** | `refined` | `frameless.RefinedFieldEncoderTests` | Encode case class with a refined optional field |

## Tests Present in Current but Not in Legacy

**Total: 449 tests**

### Within Existing Test Suites


#### Module: `cats`

- **frameless.cats.Test**: `inner pairwise monoid`
- **frameless.cats.Test**: `pair rdd numeric commutative semigroup example`
- **frameless.cats.Test**: `rdd of SortedMap[Int,Int] commutative monoid`
- **frameless.cats.Test**: `rdd simple numeric commutative semigroup`
- **frameless.cats.Test**: `rdd tuple commutative semigroup example`

### Entire Test Suites Only in Current


#### Module: `cats`

- **frameless.cats.FramelessSyntaxTests** (2 tests)
  - `dataset typed - toTyped`
  - `properties can be read back`

#### Module: `dataset`

- **frameless.AsTests** (2 tests)
  - `as[X2[A, B]]`
  - `as[X2[X2[A, B], C]`
- **frameless.BitwiseTests** (3 tests)
  - `bitwiseAND`
  - `bitwiseOR`
  - `bitwiseXOR`
- **frameless.CastTests** (1 tests)
  - `cast`
- **frameless.CheckpointTests** (1 tests)
  - `checkpoint`
- **frameless.ColTests** (3 tests)
  - `col`
  - `colMany`
  - `select colMany`
- **frameless.CollectTests** (1 tests)
  - `collect()`
- **frameless.ColumnTests** (23 tests)
  - `Consistency with Spark internal date/time representation`
  - `asCol`
  - `asCol single column TypedDatasets`
  - `asCol with numeric operators`
  - `between`
  - `boolean and / or`
  - `col through lambda`
  - `contains`
  - `endsWith`
  - `field`
  - `field compiles only for valid field`
  - `getOrElse`
  - `like`
  - `opt`
  - `opt compiles only for columns of type Option[_]`
  - `reference Value class so can join on`
  - `rlike`
  - `select('a < 'b, 'a <= 'b, 'a > 'b, 'a >= 'b)`
  - `startsWith`
  - `substr`
  - `toString`
  - `unary_!`
  - `unary_! with non-boolean columns should not compile`
- **frameless.ColumnViaLambdaTests** (12 tests)
  - `col((x: MyClass1) => x.a`
  - `col((x: MyClass1) => x.c.e.f`
  - `col((x: MyClass1) => x.toString.size) does not compile`
  - `col(_.a)`
  - `col(_.a.toString) does not compile`
  - `col(_.a.toString.size) does not compile`
  - `col(_.c.d)`
  - `col(_.c.d) as int does not compile (is long)`
  - `col(_.c.e.f)`
  - `col(_.g.h does not compile`
  - `col(x => java.lang.Math.abs(x.a)) does not compile`
  - `col(x => x.a`
- **frameless.ColumnsTests** (1 tests)
  - `columns`
- **frameless.CountTests** (1 tests)
  - `count`
- **frameless.CreateTests** (8 tests)
  - `Map fields (scala.Predef.Map / scala.collection.immutable.Map)`
  - `array fields`
  - `creation using X4 derived DataFrames`
  - `dataset with different column order`
  - `list fields`
  - `maps with Option keys should not resolve the TypedEncoder`
  - `not aligned columns should throw an exception`
  - `vector fields`
- **frameless.DistinctTests** (1 tests)
  - `distinct`
- **frameless.DropTest** (5 tests)
  - `drop four columns`
  - `fail to compile on added column name`
  - `fail to compile on different column name`
  - `fail to compile on missing value`
  - `remove column in the middle`
- **frameless.DropTupledTest** (4 tests)
  - `drop first column`
  - `drop five columns`
  - `drop last column`
  - `drop middle column`
- **frameless.EncoderTests** (4 tests)
  - `It should encode deeply nested collections`
  - `It should encode java.time.Duration`
  - `It should encode java.time.Instant`
  - `It should encode java.time.Period`
- **frameless.ExceptTests** (1 tests)
  - `except`
- **frameless.ExplodeTests** (6 tests)
  - `explode on arrays`
  - `explode on maps`
  - `explode on maps making sure no key / value naming collision happens`
  - `explode on maps preserving other columns`
  - `explode on vectors/list/seq`
  - `simple explode test`
- **frameless.FilterTests** (11 tests)
  - `Option content filter`
  - `Option equality/inequality for columns`
  - `Option equality/inequality for lit`
  - `filter with arithmetic expressions: addition`
  - `filter with arithmetic expressions: multiplication`
  - `filter with isin values`
  - `filter with values (not columns): addition`
  - `filter('a =!= 'b`
  - `filter('a =!= 'b)`
  - `filter('a =!= lit(b))`
  - `filter('a == lit(b))`
- **frameless.FirstTests** (2 tests)
  - `first`
  - `first on empty dataset should return None`
- **frameless.FlattenTests** (2 tests)
  - `different Optional types`
  - `simple flatten test`
- **frameless.GroupByTests** (14 tests)
  - `agg(sum('a))`
  - `agg(sum('a), sum('b))`
  - `agg(sum('a), sum('b), min('c), max('d))`
  - `agg(sum('a), sum('b), sum('c))`
  - `groupBy('a).agg(sum('b))`
  - `groupBy('a).agg(sum('b), sum('c)) to groupBy('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))`
  - `groupBy('a).flatMapGroups(('a, toVector(('a, 'b))`
  - `groupBy('a).mapGroups('a, sum('b))`
  - `groupBy('a).mapGroups(('a, toVector(('a, 'b))`
  - `groupBy('a, 'b).agg(sum('c)) to groupBy('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))`
  - `groupBy('a, 'b).agg(sum('c), sum('d))`
  - `groupBy('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))`
  - `groupBy('a, 'b).mapGroups('a, 'b, sum('c))`
  - `groupByMany('a).agg(sum('b))`
- **frameless.InjectionTests** (14 tests)
  - `Derive encoder for ADT with abstract class as the base type`
  - `Derive encoder for phantom type`
  - `Derive encoder for type with data constructors defined as parameterless case classes`
  - `Derive encoder for type with data constructors defined in the companion object`
  - `Injection based encoders`
  - `Resolve ambiguity by importing usingDerivation`
  - `Resolve ambiguity by importing usingInjection`
  - `Resolve missing implicit by deriving Injection instance`
  - `TypedEncoder[Employee] implicit is missing`
  - `TypedEncoder[Maybe] cannot be derived`
  - `TypedEncoder[Person] is ambiguous`
  - `apply method of derived Injection instance produces the correct string`
  - `invert method of derived Injection instance produces the correct value`
  - `invert method of derived Injection instance should throw exception if string does not match data constructor names`
- **frameless.InputFilesTests** (1 tests)
  - `inputFiles`
- **frameless.IntersectTests** (1 tests)
  - `intersect`
- **frameless.IsLocalTests** (1 tests)
  - `isLocal`
- **frameless.IsStreamingTests** (1 tests)
  - `isStreaming`
- **frameless.JobTests** (6 tests)
  - `flatMap associativity`
  - `flatMap left identity`
  - `flatMap right identity`
  - `map composition`
  - `map identity`
  - `properties read back`
- **frameless.JoinTests** (7 tests)
  - `ab.joinCross(ac)`
  - `ab.joinFull(ac)(ab.a == ac.a)`
  - `ab.joinInner(ac)(ab.a == ac.a)`
  - `ab.joinLeft(ac)(ab.a == ac.a)`
  - `ab.joinLeftAnti(ac)(ab.a == ac.a)`
  - `ab.joinLeftSemi(ac)(ab.a == ac.a)`
  - `ab.joinRight(ac)(ab.a == ac.a)`
- **frameless.LimitTests** (1 tests)
  - `limit`
- **frameless.LitTests** (4 tests)
  - `#205: comparing literals encoded using Injection`
  - `select(lit(...))`
  - `support optional value class`
  - `support value class`
- **frameless.NumericTests** (10 tests)
  - `a mod lit(b)`
  - `divide`
  - `divide BigDecimals`
  - `isNaN`
  - `isNaN with non-nan types should not compile`
  - `minus`
  - `mod`
  - `multiply`
  - `multiply BigDecimal`
  - `plus`
- **frameless.OrderByTests** (11 tests)
  - `derives a CatalystOrdered for case classes when all fields are comparable`
  - `derives a CatalystOrdered for tuples when all fields are comparable`
  - `fail when selected column is not sortable`
  - `fails to compile when one of the field isn't comparable`
  - `single column non nullable orderBy`
  - `single column non nullable partition sorting`
  - `sort support for mixed default and explicit ordering`
  - `three columns non nullable orderBy`
  - `three columns non nullable partition sorting`
  - `two columns non nullable orderBy`
  - `two columns non nullable partition sorting`
- **frameless.QueryExecutionTests** (1 tests)
  - `queryExecution`
- **frameless.RandomSplitTests** (2 tests)
  - `randomSplit(weight, seed)`
  - `randomSplitAsList(weight, seed)`
- **frameless.RecordEncoderTests** (19 tests)
  - `Case class with Map & Value class`
  - `Case class with simple Map`
  - `Case class with value class as optional field`
  - `Case class with value class field`
  - `Deeply nested optional values have correct deserialization`
  - `Dropping fields`
  - `Empty nested record value becomes none on deserialization`
  - `Empty nested record value becomes null on serialization`
  - `Encode array of Value class`
  - `Encode binary array`
  - `Encode case class with Value class`
  - `Encode case class with simple Seq`
  - `Encode simple array`
  - `Nesting with Seq`
  - `Nesting with Set`
  - `Representation skips units`
  - `Scalar value class`
  - `Serialization skips units`
  - `Unable to encode products made from units only`
- **frameless.SQLContextTests** (1 tests)
  - `sqlContext`
- **frameless.SchemaTests** (3 tests)
  - `schema of groupBy('a).agg(sum('b))`
  - `schema of select(lit(1L))`
  - `schema of select(lit(1L), lit(2L)).as[X2[Long, Long]]`
- **frameless.SelectTests** (19 tests)
  - `select with aggregation operations is not supported`
  - `select with column expression addition`
  - `select with column expression division`
  - `select with column expression multiplication`
  - `select with column expression subtraction`
  - `select('a) FROM abcd`
  - `select('a, 'b) FROM abcd`
  - `select('a, 'b, 'c) FROM abcd`
  - `select('a,'b,'c,'d) FROM abcd`
  - `select('a,'b,'c,'d,'a) FROM abcd`
  - `select('a,'b,'c,'d,'a, 'c) FROM abcd`
  - `select('a,'b,'c,'d,'a,'c,'b) FROM abcd`
  - `select('a,'b,'c,'d,'a,'c,'b, 'a) FROM abcd`
  - `select('a,'b,'c,'d,'a,'c,'b,'a,'c) FROM abcd`
  - `select('a,'b,'c,'d,'a,'c,'b,'a,'c, 'd) FROM abcd`
  - `select('a.b)`
  - `tests to cover problematic dataframe column names during projections`
  - `unary - on arithmetic`
  - `unary - on strings should not type check`
- **frameless.SelfJoinTests** (6 tests)
  - `Do you want ambiguous self join? This is how you get ambiguous self join.`
  - `colLeft and colRight are equivalent to col outside of joins`
  - `colLeft and colRight are equivalent to col outside of joins - via files (codegen)`
  - `self join with colLeft/colRight disambiguation`
  - `self join with unambiguous expression`
  - `trivial self join`
- **frameless.SparkSessionTests** (1 tests)
  - `sparkSession`
- **frameless.StorageLevelTests** (1 tests)
  - `storageLevel`
- **frameless.TakeTests** (1 tests)
  - `take`
- **frameless.ToJSONTests** (1 tests)
  - `toJSON`
- **frameless.ToLocalIteratorTests** (1 tests)
  - `toLocalIterator`
- **frameless.UnionTests** (4 tests)
  - `Align fields for case classes`
  - `Align fields for different number of columns`
  - `Union for simple data types`
  - `fail to compile on not aligned schema`
- **frameless.WithColumnTest** (6 tests)
  - `append four columns`
  - `fail to compile on added column name`
  - `fail to compile on different column name`
  - `fail to compile on missing value`
  - `fail to compile on wrong typed column`
  - `update in place`
- **frameless.WithColumnTupledTest** (1 tests)
  - `append five columns`
- **frameless.WriteStreamTests** (2 tests)
  - `write csv`
  - `write parquet`
- **frameless.WriteTests** (2 tests)
  - `write csv`
  - `write parquet`
- **frameless.forward.ForeachTests** (2 tests)
  - `foreach`
  - `foreachPartition`
- **frameless.forward.HeadTests** (1 tests)
  - `headOption(), head(1), and head(4)`
- **frameless.functions.AggregateFunctionsTests** (24 tests)
  - `approxCountDistinct`
  - `avg`
  - `collectList`
  - `collectSet`
  - `corr`
  - `count`
  - `count('a)`
  - `countDistinct`
  - `covar_pop`
  - `covar_samp`
  - `first`
  - `kurtosis`
  - `last`
  - `lit`
  - `litAggr`
  - `max`
  - `max with follow up multiplication`
  - `min`
  - `skewness`
  - `stddev and variance`
  - `stddev_pop`
  - `stddev_samp`
  - `sum`
  - `sumDistinct`
- **frameless.functions.NonAggregateFunctionsTests** (87 tests)
  - `Empty vararg tests`
  - `abs`
  - `abs big decimal`
  - `acos`
  - `arrayContains`
  - `ascii`
  - `asin`
  - `atan`
  - `atan2`
  - `atan2LitLeft`
  - `atan2LitRight`
  - `base64`
  - `bin`
  - `bitwiseNOT`
  - `bround`
  - `bround big decimal`
  - `bround big decimal with scale`
  - `bround with scale`
  - `ceil`
  - `concat`
  - `concat for TypedAggregate`
  - `concat_ws`
  - `concat_ws for TypedAggregate`
  - `conv`
  - `cos`
  - `cosh`
  - `crbt`
  - `crc32`
  - `dayofmonth`
  - `dayofweek`
  - `dayofyear`
  - `degrees`
  - `exp`
  - `factorial`
  - `floor`
  - `hour`
  - `hypot with double`
  - `hypot with two columns`
  - `inputFileName`
  - `instr`
  - `length`
  - `levenshtein`
  - `log`
  - `log with base`
  - `log10`
  - `log1p`
  - `log2`
  - `lower`
  - `lpad`
  - `ltrim`
  - `md5`
  - `minute`
  - `monotonic id`
  - `month`
  - `negate`
  - `not`
  - `pmod`
  - `pow with double`
  - `pow with two columns`
  - `quarter`
  - `regexp_replace`
  - `reverse`
  - `round`
  - `round big decimal`
  - `round big decimal with scale`
  - `round with scale`
  - `rpad`
  - `rtrim`
  - `second`
  - `sha1`
  - `sha2`
  - `shiftLeft`
  - `shiftRight`
  - `shiftRightUnsigned`
  - `signum`
  - `sin`
  - `sinh`
  - `sqrt`
  - `substring`
  - `tan`
  - `tanh`
  - `trim`
  - `unbase64`
  - `upper`
  - `weekofyear`
  - `when`
  - `year`
- **frameless.functions.UdfTests** (7 tests)
  - `five argument udf`
  - `four argument udf`
  - `multiple one argument udf`
  - `multiple two argument udf`
  - `one argument udf`
  - `three argument udf`
  - `two argument udf`
- **frameless.functions.UnaryFunctionsTest** (7 tests)
  - `size on Map`
  - `size on array test`
  - `size tests`
  - `sort in ascending order`
  - `sort in descending order`
  - `sort on array test: ascending order`
  - `sort on array test: descending order`
- **frameless.ops.ColumnTypesTest** (1 tests)
  - `test summoning`
- **frameless.ops.CubeTests** (12 tests)
  - `cube('a).agg(count())`
  - `cube('a).agg(sum('b)`
  - `cube('a).agg(sum('b), sum('c)) to cube('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))`
  - `cube('a).flatMapGroups(('a, toVector(('a, 'b))`
  - `cube('a).mapGroups('a, sum('b))`
  - `cube('a).mapGroups(('a, toVector(('a, 'b))`
  - `cube('a, 'b).agg(count())`
  - `cube('a, 'b).agg(sum('c)) to cube('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))`
  - `cube('a, 'b).agg(sum('c), sum('d))`
  - `cube('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))`
  - `cube('a, 'b).mapGroups('a, 'b, sum('c))`
  - `cubeMany('a).agg(sum('b))`
- **frameless.ops.PivotTest** (7 tests)
  - `Pivot on Boolean`
  - `Pivot with cube on Boolean`
  - `Pivot with cube on two columns, pivot on Long`
  - `Pivot with groupBy on two columns, pivot on Long`
  - `Pivot with rollup on Boolean`
  - `Pivot with rollup on two columns, pivot on Long`
  - `X4[Boolean, String, Int, Boolean] pivot on String`
- **frameless.ops.RepeatTest** (2 tests)
  - `ill typed`
  - `summoning with implicitly`
- **frameless.ops.RollupTests** (12 tests)
  - `rollup('a).agg(count())`
  - `rollup('a).agg(sum('b)`
  - `rollup('a).agg(sum('b), sum('c)) to rollup('a).agg(sum('a), sum('b), sum('a), sum('b), sum('a))`
  - `rollup('a).flatMapGroups(('a, toVector(('a, 'b))`
  - `rollup('a).mapGroups('a, sum('b))`
  - `rollup('a).mapGroups(('a, toVector(('a, 'b))`
  - `rollup('a, 'b).agg(count())`
  - `rollup('a, 'b).agg(sum('c)) to rollup('a, 'b).agg(sum('c),sum('c),sum('c),sum('c),sum('c))`
  - `rollup('a, 'b).agg(sum('c), sum('d))`
  - `rollup('a, 'b).flatMapGroups((('a,'b) toVector((('a,'b), 'c))`
  - `rollup('a, 'b).mapGroups('a, 'b, sum('c))`
  - `rollupMany('a).agg(sum('b))`
- **frameless.ops.SmartProjectTest** (4 tests)
  - `X3U to X1,X2,X3 projections`
  - `X4 to X1,X2,X3,X4 projections`
  - `project Foo to Bar`
  - `project to InvalidFooProjection should not type check`
- **frameless.ops.deserialized.FilterTests** (1 tests)
  - `filter`
- **frameless.ops.deserialized.FlatMapTests** (1 tests)
  - `flatMap`
- **frameless.ops.deserialized.MapPartitionsTests** (1 tests)
  - `mapPartitions`
- **frameless.ops.deserialized.MapTests** (1 tests)
  - `map`
- **frameless.ops.deserialized.ReduceTests** (2 tests)
  - `reduce Int`
  - `reduce String`
- **frameless.sql.rules.FramelessLitPushDownTests** (3 tests)
  - `java.sql.Timestamp push-down`
  - `java.time.Instant push-down`
  - `struct push-down`
- **frameless.syntax.FramelessSyntaxTests** (2 tests)
  - `dataset typed - toTyped`
  - `frameless typed column and aggregate`

#### Module: `ml`

- **frameless.ml.TypedEncoderInstancesTests** (3 tests)
  - `Matrix encoding is injective using collect()`
  - `Vector encoding is injective using collect()`
  - `Vector is encoded as VectorUDT and thus can be run in a Spark ML model`
- **frameless.ml.classification.ClassificationIntegrationTests** (1 tests)
  - `predict field3 from field1 and field2 using a RandomForestClassifier`
- **frameless.ml.classification.TypedRandomForestClassifierTests** (3 tests)
  - `create() compiles only with correct inputs`
  - `fit() returns a correct TypedTransformer`
  - `param setting is retained`
- **frameless.ml.clustering.BisectingKMeansTests** (2 tests)
  - `fit() returns a correct TypedTransformer`
  - `param setting is retained`
- **frameless.ml.clustering.ClusteringIntegrationTests** (2 tests)
  - `predict field2 from field1 using a K-means clustering`
  - `predict field2 from field1 using a bisecting K-means clustering`
- **frameless.ml.clustering.KMeansTests** (2 tests)
  - `fit() returns a correct TypedTransformer`
  - `param setting is retained`
- **frameless.ml.feature.TypedIndexToStringTests** (2 tests)
  - `.transform() correctly transform an input dataset`
  - `create() compiles only with correct inputs`
- **frameless.ml.feature.TypedStringIndexerTests** (3 tests)
  - `.fit() returns a correct TypedTransformer`
  - `create() compiles only with correct inputs`
  - `param setting is retained`
- **frameless.ml.feature.TypedVectorAssemblerTests** (2 tests)
  - `.transform() returns a correct TypedTransformer`
  - `create() compiles only with correct inputs`
- **frameless.ml.regression.RegressionIntegrationTests** (1 tests)
  - `predict field3 from field1 and field2 using a RandomForestRegressor`
- **frameless.ml.regression.TypedLinearRegressionTests** (4 tests)
  - `TypedLinearRegressor should fit straight line`
  - `create() compiles only with correct inputs`
  - `fit() returns a correct TypedTransformer`
  - `param setting is retained`
- **frameless.ml.regression.TypedRandomForestRegressorTests** (3 tests)
  - `create() compiles only with correct inputs`
  - `fit() returns a correct TypedTransformer`
  - `param setting is retained`

#### Module: `refined`

- **frameless.RefinedFieldEncoderTests** (3 tests)
  - `Encode a bare refined type`
  - `Encode case class with a refined field`
  - `Encode case class with a refined optional field`

---

*Generated by Missing Tests Comparison Tool*
