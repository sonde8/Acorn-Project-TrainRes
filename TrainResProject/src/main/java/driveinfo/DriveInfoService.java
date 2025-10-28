package driveinfo;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;

public class DriveInfoService {

    private DriveInfoDAO dao = new DriveInfoDAO();

    public ArrayList<DriveInfoResultDTO> getDriveInfosByRoutePaging(
            String deptName,
            String arriName,
            String startTimeFilter,
            int offset,
            int limit
    ) {
        ArrayList<DriveInfoResultDTO> list =
                dao.findAllByRoutePaging(deptName, arriName, startTimeFilter, offset, limit);

        for (DriveInfoResultDTO dto : list) {
            Timestamp dept = dto.getOriginalDeptTime();
            Timestamp arri = dto.getOriginalArriTime();

            if (dept != null && arri != null) {

                Duration duration = Duration.between(dept.toLocalDateTime(), arri.toLocalDateTime());

                if (duration.isNegative()) {
                    duration = duration.plusHours(24);
                }

                long totalMinutes = duration.toMinutes();
                long hours = totalMinutes / 60;
                long minutes = totalMinutes % 60;

                String durationStr = hours + "시간" + minutes + "분";
                dto.setDurationStr(durationStr);
            }
        }

        return list;
    }

    public DriveInfoResultDTO getDriveInfoById(int driveId) {
        DriveInfoResultDTO dto = dao.findByDriveId(driveId);

        if (dto != null) {
            Timestamp dept = dto.getOriginalDeptTime();
            Timestamp arri = dto.getOriginalArriTime();

            if (dept != null && arri != null) {
                Duration duration = Duration.between(dept.toLocalDateTime(), arri.toLocalDateTime());

                if (duration.isNegative()) {
                    duration = duration.plusHours(24);
                }

                long totalMinutes = duration.toMinutes();
                long hours = totalMinutes / 60;
                long minutes = totalMinutes % 60;

                String durationStr = hours + "시간" + minutes + "분";
                dto.setDurationStr(durationStr);
            }
        }
        return dto;
    }
}
