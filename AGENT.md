# Frameless Agent Guide

## Build/Test Commands
- Build: `sbt "Test/compile"` (also for build errors)
- Test: `sbt "Test/test"` (all tests)  
- Single test: `sbt "testOnly frameless.TypedDatasetTest"`
- Console: `sbt console` (launches with Spark context)

## Architecture & Structure
Frameless is a Scala 2.13 library for typed Spark operations with multiple subprojects:
- `core/` - Core abstractions (Injection, etc.)
- `dataset/` - Main TypedDataset API (strongly typed DataFrame operations)
- `cats/` - Cats integration for RDD operations
- `ml/` - Typed Spark ML API
- `refined/` - Refined types integration
- Multi-Spark support: separate artifacts for Spark 3.3, 3.4, 3.5

## Code Style & Conventions
- Scala 2.13 syntax only (NO Scala 3 features)
- Follow .scalafmt.conf formatting
- Key imports: `import frameless.functions._`, `import frameless.syntax._`
- Type-safe encoders via `TypedEncoder`
- Use `TypedDataset` instead of raw `Dataset`
- Prefer `import _root_.cats._` in cats modules
- Use `import org.apache.spark.sql.{functions => sparkFunctions}` to avoid conflicts
