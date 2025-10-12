# Migration

- switch to scala 3 syntax




- 

make the following migration of package usage: 

- `shapeless.ops.hlist` -> `formless.hilst`.
- when yuo encounter an implicit not found error, define a new trait with the suffix `_Pending` with an unimplemented implicit function as a placeholder to make it compile
