package staff;

import report.BaseReport;

public interface StaffReport extends BaseReport{
  public void generateParticipantReport();
  public void generatePerformanceReport(String format);
  public void generateEnquiryReport();
}
