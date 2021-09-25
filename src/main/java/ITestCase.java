import java.util.List;

public interface ITestCase {

  public String getTestCaseId();

  public String getClassPath();

  public String getTestSummary();

  public String getTestDescription();

  public String getTestSuite();

  public String getTestComponent();

  public String getTestTag();

  public String getTestError();

  public List<String> getTestIssueLinkKeys();

  public List<String> getUnverifiedTestIssueLinkKeys();

  public String toString();

  public String getTestGroups();
}
