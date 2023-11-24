package migrations.v3;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;

public class R__java_r3 extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        try (Statement insert = context.getConnection().createStatement()) {
            insert.execute("INSERT INTO repeatable (comment) VALUES ('I executed from Java v3')");
        }
    }
}
