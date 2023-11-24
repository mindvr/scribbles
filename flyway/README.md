Happy scenarios. 

1. v1 -> v1 : doing nothing when nothing's changed
2. v1 -> (v1.5) -> v2 -> v3 : forward migration
3. v3: baseline migration

Failure modes:
1. Duplicated versions: f1
2. checksum changes: v1 -> f2
3. baseline only + existing flyway log v1 -> f3 (compared to --clear f3)
4. sql fail v1 -> f4, check db
5. java fail v1 -> f5, check db (transaction per migration)