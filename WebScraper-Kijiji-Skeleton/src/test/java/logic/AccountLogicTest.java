package logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entity.Account;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;  
import java.util.HashMap;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Shariar
 */
class AccountLogicTest {

    private static Tomcat tomcat;
    private AccountLogic logic;
    private Map<String,String[]>sampleMap; 

    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        System.out.println(new File("src\\main\\webapp\\").getAbsolutePath());
        tomcat = new Tomcat();
        tomcat.enableNaming();
        tomcat.setPort(8080);
        Context context = tomcat.addWebapp("/WebScraper", new File("src\\main\\webapp").getAbsolutePath());
        context.addApplicationListener("dal.EMFactory");
        tomcat.init();
        tomcat.start();

        

    }

    @AfterAll
    final static void tearDownAfterClass() throws Exception {
        tomcat.stop();
    }

    @BeforeEach
    final void setUp() throws Exception {

        logic = new AccountLogic();
        sampleMap = new HashMap<String, String[]>();
        sampleMap.put(AccountLogic.DISPLAY_NAME, new String[]{"JUnit 5 Test"});
        sampleMap.put(AccountLogic.USER, new String[]{"junit"});
        sampleMap.put(AccountLogic.PASSWORD, new String[]{"jUnit5"});
    }

    @AfterEach
    final void tearDown() throws Exception {
    }

    /**
     * Test of getAll method, of class AccountLogic.
     */
    @Test
    final void testGetAll() {
        System.out.println("getAll");
        //get all the accounts from the DB
        List<Account> list = logic.getAll();
        //store the size of list/ this way we know how many accounts exits in DB
        int originalSize = list.size();
        //create a new Account and save it so we can delete later
        Account testAccount = logic.createEntity(sampleMap);
        //add the newly created account to DB
        logic.add(testAccount);
        //get all the accounts again (the new list)
        list = logic.getAll();
        //the new size of accounts must be 1 larger than original size
        assertEquals(originalSize + 1, list.size());
        //delete the new account, so DB is reverted back to it original form
        logic.delete(testAccount);
        //get all accounts again
        list = logic.getAll();
        //the new size of accounts must be same as original size
        assertEquals(originalSize, list.size());

    }
    
    /**
     * Test of getWithId method, of class AccountLogic.
     */
    @Test
    final void testGetWithID() {
        System.out.println("getWithID");
        //get all accounts
        List<Account> list = logic.getAll();
        //use the first account in the list as test Account
        Account testAccount = list.get(0);
        //using the id of test account get another account from logic
        Account returnedAccount = logic.getWithId(testAccount.getId());
        //the two accounts (testAcounts and returnedAccounts) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testAccount.getId(), returnedAccount.getId());
        assertEquals(testAccount.getDisplayName(), returnedAccount.getDisplayName());
        assertEquals(testAccount.getUser(), returnedAccount.getUser());
        assertEquals(testAccount.getPassword(), returnedAccount.getPassword());

    }
    
    /**
    * Test of getAccountWithDisplayName method, of class AccountLogic.
    */
    @Test
    final void testGetAccountWithDisplayName(){
        System.out.println("getAccountWithDisplayName");
        //get all accounts
        List<Account> list = logic.getAll();
        //use the first account in the list as test Account
        Account testAccount = list.get(0);
        //using the display name of test account get another account from logic
        Account returnedAccount = logic.getAccountWithDisplayName(testAccount.getDisplayName());
        //the two accounts (testAcounts and returnedAccounts) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testAccount.getId(), returnedAccount.getId());
        assertEquals(testAccount.getDisplayName(), returnedAccount.getDisplayName());
        assertEquals(testAccount.getUser(), returnedAccount.getUser());
        assertEquals(testAccount.getPassword(), returnedAccount.getPassword());
        
    }
      
    /**
     * Test of getAccountWIthUser method, of class AccountLogic.
     */
    @Test
    final void testGetAccountWithUser(){
        System.out.println("getAccountWithUser");
        // get all accounts
        List<Account> list = logic.getAll();
        //use the first account in the list as test Account
        Account testAccount = list.get(0);
        //using the user of test account get another account from logic
        Account returnedAccount = logic.getAccountWIthUser(testAccount.getUser());
        //the two accounts (testAcounts and returnedAccounts) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testAccount.getId(), returnedAccount.getId());
        assertEquals(testAccount.getDisplayName(), returnedAccount.getDisplayName());
        assertEquals(testAccount.getUser(), returnedAccount.getUser());
        assertEquals(testAccount.getPassword(), returnedAccount.getPassword());
    }
    
    /**
     * Test of getAccountsWithPassword method, of class AccountLogic.
     */
    @Test
    public void testGetAccountsWithPassword() {
        // get all accounts
        List<Account> list = logic.getAll();
        //use the first account in the list as test Account
        Account testAccount = list.get(0);
        //using the password of test account get another accounts from logic
        List<Account> returnedAccounts = logic.getAccountsWithPassword(testAccount.getPassword());
        
        //the two accounts (testAcounts and returnedAccounts) must be the same
        //assert the password feild to guarantee they has the same password and find the expected account
        Account resultAccount = null;
        for (Account acc : returnedAccounts) {
            assertEquals(testAccount.getPassword(), acc.getPassword());
            if(acc.getId().equals(testAccount.getId()) && acc.getDisplayName().equals(testAccount.getDisplayName())
                    && acc.getUser().equals(testAccount.getUser()) && acc.getPassword().equals(testAccount.getPassword())) {
                resultAccount = acc;
            }
        }
        //the two accounts (testAcounts and resultAccount) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testAccount.getId(), resultAccount.getId());
        assertEquals(testAccount.getDisplayName(), resultAccount.getDisplayName());
        assertEquals(testAccount.getUser(), resultAccount.getUser());
        assertEquals(testAccount.getPassword(), resultAccount.getPassword());
        
    }

    /**
     * Test of getAccountWith method, of class AccountLogic.
     */
    @Test
    public void testGetAccountWith() {
        System.out.println("getAccountWith");
        // get all accounts
        List<Account> list = logic.getAll();
        //use the first account in the list as test Account
        Account testAccount = list.get(0);
        //using the user of test account get another account from logic
        Account returnedAccount = logic.getAccountWith(testAccount.getUser(), testAccount.getPassword());
        //the two accounts (testAcounts and returnedAccounts) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testAccount.getId(), returnedAccount.getId());
        assertEquals(testAccount.getDisplayName(), returnedAccount.getDisplayName());
        assertEquals(testAccount.getUser(), returnedAccount.getUser());
        assertEquals(testAccount.getPassword(), returnedAccount.getPassword());
        
    }

    /**
     * Test of search method, of class AccountLogic.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        
        // get all accounts
        List<Account> list = logic.getAll();
        //use the first account in the list as test Account
        Account testAccount = list.get(0);
        
        // get the test account user as the search key
        String search = testAccount.getUser();
        
        // expected account
        Account expResult = null;
        
        // search result list
        List<Account> result = logic.search(search);
        
        // we find whether the list contains our test account
        for (Account acc : result) {
            if(acc.getId().equals(testAccount.getId()) && acc.getDisplayName().equals(testAccount.getDisplayName())
                    && acc.getUser().equals(testAccount.getUser()) && acc.getPassword().equals(testAccount.getPassword())) {
                expResult = acc;
            }
        }
        
        //the two accounts (testAcount and expResult) must be the same
        //assert all field to guarantee they are the same
        assertEquals(testAccount.getId(), expResult.getId());
        assertEquals(testAccount.getDisplayName(), expResult.getDisplayName());
        assertEquals(testAccount.getUser(), expResult.getUser());
        assertEquals(testAccount.getPassword(), expResult.getPassword());
        
    }

    /**
     * Test of createEntity method, of class AccountLogic.
     */
    @Test
    public void testCreateEntity() {
        
        System.out.println("createEntity");
        //get all the accounts from the DB
        List<Account> list = logic.getAll();
        //store the size of list/ this way we know how many accounts exits in DB
        int originalSize = list.size();
        //create a new Account and save it so we can delete later
        Account testAccount = logic.createEntity(sampleMap);
        //add the newly created account to DB
        logic.add(testAccount);
        //get all the accounts again (the new list)
        list = logic.getAll();
        
        //the new size of accounts must be 1 larger than original size
        assertEquals(originalSize + 1, list.size());
        
        // get the last account in the db, it should be the newly added account
        Account returnedAccount = list.get(list.size());
        
        assertEquals(testAccount.getId(), returnedAccount.getId());
        assertEquals(testAccount.getDisplayName(), returnedAccount.getDisplayName());
        assertEquals(testAccount.getUser(), returnedAccount.getUser());
        assertEquals(testAccount.getPassword(), returnedAccount.getPassword());
        
        //delete the new account, so DB is reverted back to it original form
        logic.delete(testAccount);
        
    }

    /**
     * Test of getColumnNames method, of class AccountLogic.
     */
    @Test
    public void testGetColumnNames() {
        System.out.println("getColumnNames");
        List<String> expResult = Arrays.asList("ID", "Display Name", "User", "Password");
        List<String> result = logic.getColumnNames();
        int expSize = expResult.size();
        int size = result.size();
        //assert the column size and all columns to guarantee they are the same
        assertEquals(expSize, size);
        for (int i = 0; i < expSize; i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
    }

    /**
     * Test of getColumnCodes method, of class AccountLogic.
     */
    @Test
    public void testGetColumnCodes() {
        List<String> expResult = Arrays.asList(AccountLogic.ID, AccountLogic.DISPLAY_NAME, 
                AccountLogic.USER, AccountLogic.PASSWORD);
        List<String> result = logic.getColumnNames();
        int expSize = expResult.size();
        int size = result.size();
        //assert the column codes size and all columns code to guarantee they are the same
        assertEquals(expSize, size);
        for (int i = 0; i < expSize; i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
    }

    /**
     * Test of extractDataAsList method, of class AccountLogic.
     */
    @Test
    public void testExtractDataAsList() {
        System.out.println("extractDataAsList");
        // get all accounts
        List<Account> list = logic.getAll();
        //use the first account in the list as test Account
        Account testAccount = list.get(0);
        
        List expResult = Arrays.asList(testAccount.getId(), testAccount.getDisplayName(), 
                testAccount.getUser(), testAccount.getPassword());
        
        List result = logic.extractDataAsList(testAccount);
        
        //the two lists (expResult and result) must be the same
        //assert all field to guarantee they are the same
        int expSize = expResult.size();
        int size = result.size();
        //assert the lists sizes and all elements to guarantee they are the same
        assertEquals(expSize, size);
        for (int i = 0; i < expSize; i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
        
    }
    
}