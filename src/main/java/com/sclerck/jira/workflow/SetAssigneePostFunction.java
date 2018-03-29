
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.IssueService.AssignValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

/**
 * This is the post-function class that gets executed at the end of the
 * transition. Any parameters that were saved in your factory class will be
 * available in the transientVars Map.
 */
public class SetAssigneePostFunction extends AbstractJiraFunctionProvider {
	private static final Logger log = LoggerFactory.getLogger(SetAssigneePostFunction.class);

	private UserManager userManager;
	private IssueService issueService;

	public SetAssigneePostFunction() {
		userManager = ComponentAccessor.getUserManager();
		issueService = ComponentAccessor.getIssueService();
	}

	public void execute(Map transientVars, Map args, PropertySet ps) throws WorkflowException {
		MutableIssue issue = getIssue(transientVars);

		String user = ""; // Get the user here

		ApplicationUser userObject = userManager.getUserByKey(user);

		AssignValidationResult result = issueService.validateAssign(userObject, issue.getId(), user);

		// TODO check the result

		issue.setAssignee(userObject);
	}
}