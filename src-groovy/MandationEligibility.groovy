import org.bonitasoft.engine.api.APIAccessor

class MandationEligibility {
	
	static boolean noMatch(long id1, long id2, long id3, long id4) {
		return id1 != id2 && id1 != id3 && id1 != id4 && id2 != id3 && id2 != id4 && id3 != id4;
	}
	
	static boolean noMatch(String id1, String id2, String id3) {
		return id1 != id2 && id1 != id3 && id2 != id3;
	}
	
	static String fullName(APIAccessor apiAccessor, long ident) {
		def user = BonitaUsers.getUser(apiAccessor, ident);
		return "${user.firstName} ${user.lastName}";
	}
}
