package migrations.f5;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class V4__DefaultHireDate extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        int i = 0;
        try (Statement select = context.getConnection().createStatement()) {
            try (ResultSet rows = select.executeQuery("select empno from emp where hiredate is null")) {
                while (rows.next()) {
                    i++;
                    if (i % 2 == 0) {
                        throw new RuntimeException();
                    }
                    int empno = rows.getInt(1);

                    String sql = "UPDATE emp SET hiredate = ? WHERE empno = ?";
                    try (PreparedStatement update = context.getConnection().prepareStatement(sql)) {
                        update.setString(1, "1970-01-01");
                        update.setInt(2, empno);
                        update.execute();
                    }
                }
            }
        }
    }
}
