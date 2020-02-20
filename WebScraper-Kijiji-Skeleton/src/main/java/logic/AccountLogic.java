package logic;

import dal.AccountDAL;
import entity.Account;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shariar (Shawn) Emami
 */
public class AccountLogic extends GenericLogic<Account,AccountDAL>{
    
    public static final String DISPLAY_NAME = "displayName";
    public static final String PASSWORD = "password";
    public static final String USER = "user";
    public static final String ID = "id";
    
    public AccountLogic() {
        super(new AccountDAL());
    }
    
    @Override
    public List<Account> getAll(){
        return get(()->dal().findAll());
    }
    
    @Override
    public Account getWithId( int id){
        return get(()->dal().findById(id));
    }
    
    public Account getAccountWithDisplayName( String displayName){
        return get(()->dal().findByDisplayName( displayName));
    }
    
    public Account getAccountWIthUser( String user){
        return get(()->dal().findByUser(user));
    }
    
    public List<Account> getAccountsWithPassword( String password){
        return get(()->dal().findByPassword(password));
    }
    
    public Account getAccountWith( String username, String password){
        return get(()->dal().validateUser( username, password));
    }
    
    @Override
    public List<Account> search( String search){
        return get(()->dal().findContaining(search));
    }

    @Override
    public Account createEntity(Map<String, String[]> parameterMap) {
        Account account = new Account();
        if(parameterMap.containsKey(ID)){
            account.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        account.setDisplayName(parameterMap.get(DISPLAY_NAME)[0]);
        account.setUser(parameterMap.get(USER)[0]);
        account.setPassword(parameterMap.get(PASSWORD)[0]);
        return account;
    } 

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "Display Name", "User", "Password");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, DISPLAY_NAME, USER, PASSWORD);
    }

    @Override
    public List<?> extractDataAsList( Account e) {
        return Arrays.asList(e.getId(), e.getDisplayName(), e.getUser(), e.getPassword());
    }
}
