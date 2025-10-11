_~~# AGENTS

This document guides automated agents and contributors working in this repository.

## Scope and Goals

- **Purpose**: Maintain and evolve the typed Spark APIs in `frameless-*` modules while preserving binary-compatibility
  and code quality.
- **Style**: Follow `.scalafmt.conf` formatting and Typelevel ecosystem conventions.
- **Safety**: Avoid breaking public APIs and accidental publishing. Respect the Typelevel Code of Conduct.

## Repository Map

- **Modules**:
    - `core/` → `frameless-core`
    - `dataset/` → `frameless-dataset`
    - `cats/` → `frameless-cats`
    - `ml/` → `frameless-ml`
    - `refined/` → `frameless-refined`
- **Spark-cross variants**:
    - `*-spark33/`, `*-spark34/` modules and aggregate roots: `.spark33/` (`root-spark33`), `.spark34/` (
      `root-spark34`), `.spark35/` (`root-spark35`)
- **CI/Release config**: `.github/workflows/*.yml`, `github.sbt`
- **Docs site**: `mdocs/` (Typelevel Site)

## Guardrails (Do/Don’t)

- **Do**
    - **Commit** code to git local repository after each task, each commit message should start with your model name-version. 
    - **Format** code with `scalafmt` before committing.
    - **Test** across supported Scala and Spark roots used in CI.
    - **Run** MiMa checks on changed modules.
    - **Keep Spark deps Provided**; do not add Spark as compile dependency.
    - **Document** user-facing changes; add/adjust tests accordingly.
- **Don’t**
    - Don’t change CI secrets or publish settings.
    - Don’t break binary compatibility without coordination and proper MiMa filters and versioning.
    - Don’t introduce heavy dependencies into core APIs.
    - Don’t bypass the aggregate root projects; build/test the correct `root-sparkXX` target.
    - Don't write experimental code outside test directory, always clean them up after to avoid breaking the project compilation

## Local Environment

- **Java**: JDK 8 (CI uses Temurin 8).
- **Scala**: 2.13 only.
- **sbt**: 1.x.
- **Env (helpful)**:
    - `SBT_OPTS="-Xms1g -Xmx4g"`
    - `SPARK_LOCAL_IP=localhost` (align with CI)
    - Property tests:
        - `FRAMELESS_GEN_MIN_SIZE` (default 0)
        - `FRAMELESS_GEN_SIZE_RANGE` (default 20)

## Common Workflows (copy-paste friendly)

- **Format & Lint**
    - `sbt scalafmtAll scalafmtSbt`
    - `sbt scalafixAll`  ← run after edits
- **Compile & Test**
    - `sbt compile test`
- **Re-run previously failed tests**
    - `sbt testQuick`
- **Binary compatibility (MiMa)**
    - `sbt 'project <module>' mimaReportBinaryIssues`
- **Coverage (optional local match to CI)**
    - `sbt coverage test coverageReport`
- **Docs site (check locally)**
    - `sbt docs/tlSite`
- **REPL bootstrap**
    - `sbt console` (preloads Spark session helpers via build-defined initial commands)

## CI Expectations (see `.github/workflows/ci.yml`)

- **Matrix**: Scala 2.13 only.
- **Checks**:
    - Formatting: `scalafmtCheckAll` and sbt file check.
    - Tests with coverage, MiMa, API docs generation.
    - Codecov upload.
- **Publish**: On tags `v*` and `master` branch (handled by `sbt-typelevel-ci-release`).

## PR Protocol

- **Review**: At least one maintainer sign-off required (see `README.md` maintainers list).
- **Labels**: Use Release Drafter labels (`feature`, `enhancement`, `dependency-update`, `fix`, `bug`; avoid
  `skip-changelog` unless intended).
- **Content**:
    - Summarize change, risk, API impact, and tests.
    - Note any MiMa filters or doc updates.
    - Include reproducible steps/commands and environment variables if relevant.

## Module Guidance

- **Spark dependencies**: Stay in `Provided` scope via `sparkDependencies`/`sparkMlDependencies`.
- **Cross-modules**: If changing sources mirrored in `*-spark33/34` variants, replicate or refactor appropriately.
- **Binary Compatibility**: Adjust `mimaBinaryIssueFilters` only with justification and in the affected module.
- **Docs**: Update `mdocs/` and linked `docs/*.md` when behavior/APIs visible to users change.

## Troubleshooting

- OOM in property tests: tune `FRAMELESS_GEN_MIN_SIZE` / `FRAMELESS_GEN_SIZE_RANGE`.
- Cache issues: run `sbt +update`.
- Spark local issues: ensure `SPARK_LOCAL_IP=localhost`.

## Attribution and Conduct

- License: Apache-2.0.
- Code of Conduct: Typelevel CoC applies to all interactions._~~
