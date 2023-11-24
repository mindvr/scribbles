Happy scenarios. 

1. v1 -> v1 : doing nothing when nothing's changed
2. v1 -> (v1.5) -> v2 -> v3 : forward migration
3. v3: baseline migration

Failure modes:
1. Duplicated versions: f1
2. checksum changes: v1 -> f2
3. baseline only + existing flyway log
4. sql fail
5. java fail