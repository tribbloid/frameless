# Java Reflection Usage Report

This document lists all detected usages of Java reflection across the repository, with exact file paths and line numbers (relative to repo root).

Scanned patterns include:
- Class loading: `Class.forName(...)`
- Member lookup: `.getMethod(...)`, `.getDeclaredMethod(...)`, `.getField(...)`, `.getDeclaredField(...)`, `.getConstructor(...)`, `.getDeclaredConstructor(...)`, `.getConstructors`, `.getMethods`, `.getDeclaredMethods`, `.getFields`, `.getDeclaredFields`
- Invocation and access: `.invoke(...)`, `.setAccessible(...)`, `.newInstance(...)`
- Type info: `.getParameterTypes`, `.getParameterCount`, `.getReturnType`
- Explicit references to `java.lang.reflect.*`

No usages found for: `Proxy.newProxyInstance(...)`, `Array.newInstance(...)`.

---

## Main sources

- **dataset/src/main/scala/org/apache/spark/sql/FramelessInternals.scala**
  - Class.forName: L35, L150, L347, L510, L515, L572
  - getMethod: L37, L63, L72, L86, L96, L126, L137, L154, L165, L167, L258, L260, L321, L569, L573, L594, L606, L654
  - invoke: L39, L64, L73, L88, L98, L128, L139, L156, L169, L170, L226, L232, L261, L262, L323, L434, L438, L570, L574, L595, L607, L656
  - getField: L151
  - getConstructor: L49, L489, L526, L533
  - getConstructors: L351, L549, L556
  - getMethods: L209, L212, L215, L219, L222, L417, L433, L437
  - getParameterCount: L357
  - getParameterTypes: L543, L550, L558
  - java.lang.reflect.Constructor type refs: L522, L530

- **dataset/src/main/scala/frameless/TypedExpressionEncoder.scala**
  - Class.forName: L95, L109, L126, L129, L135, L170
  - getField: L96, L110, L129, L138, L171
  - getMethod: L97, L111, L121, L139, L172
  - invoke: L98, L115, L122, L143, L173
  - getConstructors: L127
  - newInstance (Constructor/reflective): L130, L166, L204, L247

- **dataset/src/main/scala/frameless/internal/SparkCompat.scala**
  - Class.forName: L23, L32, L39, L40, L59
  - getField: L33, L40
  - getMethod: L34
  - invoke: L35
  - getConstructor: L42, L61 (declared)
  - getConstructors: L73, L83
  - getParameterTypes: L75, L85
  - newInstance (Constructor/reflective): L54, L62
  - java.lang.reflect.Constructor type ref: L72

- **dataset/src/main/scala/org/apache/spark/sql/Spark40DatasetHelper.scala**
  - Class.forName: L38
  - getField: L39
  - getMethods: L42, L73, L103, L113
  - getParameterTypes: L44
  - getParameterCount: L49, L78, L124
  - setAccessible: L77, L121
  - invoke: L53, L58, L83, L86, L90, L105, L126

## Tests and debug utilities

- **dataset/src/test/spark-3.3+/frameless/sql/rules/FramelessLitPushDownTests.scala**
  - getMethod: L16, L27
  - invoke: L17, L28

- **dataset/src/test/scala/frameless/debug/DumpConstructors.scala**
  - Class.forName: L14, L21
  - getField: L22
  - getDeclaredMethods: L23
  - getParameterTypes: L25
  - getReturnType: L25

- **dataset/src/test/scala/frameless/debug/DumpEncoderTag.scala**
  - getMethods: L9
  - invoke: L10, L11

---

Generated automatically via ripgrep over Scala sources for common Java reflection APIs and patterns.
