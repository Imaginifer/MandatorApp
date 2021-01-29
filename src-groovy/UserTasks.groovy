import com.company.model.Mandate
import com.company.model.MandateDAO
import groovy.json.JsonBuilder

import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance
import org.bonitasoft.engine.identity.UserSearchDescriptor
import org.bonitasoft.engine.search.SearchOptionsBuilder
import org.bonitasoft.web.extension.rest.RestAPIContext
import org.bonitasoft.web.extension.rest.RestApiController
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import javax.servlet.http.HttpServletRequest

class UserTasks implements RestApiController{

	@Override
	public RestApiResponse doHandle(HttpServletRequest req, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
		
		def apiClient = context.apiClient
		def processApi = apiClient.processAPI
		def mandateDao = context.apiClient.getDAO(MandateDAO.class)
		
		def ident = context.apiSession.userId
		
		List<Map<String, String>> taskList = []
		def srx = new SearchOptionsBuilder(0, 100)
		List<HumanTaskInstance> userTasks = processApi.searchPendingTasksAssignedToUser(ident, srx.done()).result
		for (t in userTasks) {
			
			def taskInfo = [taskName: t.displayName, taskId: t.id, desc: t.displayDescription]
			taskList << taskInfo
		}
		def response = [taskList: taskList]
		responseBuilder.with {
            withResponse new JsonBuilder(response).toPrettyString()
            build()
        }
		//return null;
	}  //path: ../API/extension/userTaskLister
}
