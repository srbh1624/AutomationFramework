package ReportGenerator;

public class Update_TestReport {
	private String TestCaseName;
	private String description;
	private String status;
	
	public Update_TestReport(String TestCaseName, String description, String status) {
		this.setTestCaseName(TestCaseName);
		this.setDescription(description);
		this.setStatus(status);
	}

	public String getTestCaseName() {
		return TestCaseName;
	}

	public void setTestCaseName(String TestCaseName) {
		this.TestCaseName = TestCaseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

}
