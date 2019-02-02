package ee.ituk.omniva.userService;

public class UserService {

    /**
        If user selects mentor, then these methods should be called out
     */
    public String getEmailRecipientAadress(Integer mentorId) {
        return ""; // TODO mentor email from database using mentorId
    }

    public String getMinionName(Integer userId) {
        return ""; // TODO: get minion name from database using userId
    }

    public String getMinionEmail(Integer userId) {
        return ""; // TODO: get minion email from database using userId
    }
}
