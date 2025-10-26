package driveinfo;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;

public class DriveInfoService { 
    
    private DriveInfoDAO dao = new DriveInfoDAO();
    
    // 시간 필터링을 위해 startTimeFilter를 추가한 상태
    public ArrayList<DriveInfoResultDTO> getDriveInfosByRoutePaging(String deptName, String arriName, String startTimeFilter, int offset, int limit){
        ArrayList<DriveInfoResultDTO> list = dao.findAllByRoutePaging(deptName, arriName, startTimeFilter, offset, limit);
        
        // 소요 시간 
        for(DriveInfoResultDTO dto : list) {
        	Timestamp dept = dto.getOriginalDeptTime();
        	Timestamp arri = dto.getOriginalArriTime();
        	
        	if(dept != null && arri != null) {
        		
        		// 시간 계산
        		Duration duration = Duration.between(dept.toLocalDateTime(), arri.toLocalDateTime());
        		
        		// 다음 날 도착인 경우 24시간 더해주기
        		if(duration.isNegative()) {
        			duration = duration.plusHours(24);
        		}
        		
        		long totalMinutes = duration.toMinutes();
        		long hours = totalMinutes / 60;
        		long minutes = totalMinutes % 60;
        		
        		// DTO에 저장
        		String durationStr = hours + "시간" + minutes + "분";
        		dto.setDurationStr(durationStr);
        	}
        }

        return list;
    }
    
    // DRIVE_ID로 단일 운행 정보 조회하는 메서드 
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