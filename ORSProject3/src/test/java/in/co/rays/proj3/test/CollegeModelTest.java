/*
 * package in.co.rays.proj3.test;
 * 
 * import java.sql.Timestamp; import java.util.ArrayList; import java.util.Date;
 * import java.util.Iterator; import java.util.List;
 * 
 * import in.co.rays.proj3.dto.CollegeDTO; import
 * in.co.rays.proj3.model.CollegeModelInt; import
 * in.co.rays.proj3.model.CollegeModelJDBCImpl; import
 * in.co.rays.proj3.model.ModelFactory; import
 * in.co.rays.proj3.model.UserModelInt; import
 * in.co.rays.proj3.exception.ApplicationException; import
 * in.co.rays.proj3.exception.DatabaseException; import
 * in.co.rays.proj3.exception.DuplicateRecordException;
 * 
 *//**
	 * 
	 * College Model Test classes
	 * 
	 * @author PAWAN_TECHNIQUES
	 *
	 */
/*
 * public class CollegeModelTest {
 * 
 * // Model object to test public static CollegeModelInt model =
 * ModelFactory.getInstance().getCollegeModel();
 * 
 *//**
	 * Main method to call test methods.
	 * 
	 * @param args
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
/*
 * public static void main(String[] args) throws DuplicateRecordException,
 * DatabaseException { // model1.nextPK(); // testFindByPK(); //
 * testFindByName(); // testAdd(); // testUpdate(); // testDelete(); //
 * testSearch(); // testList(); }
 * 
 *//**
	 * Tests find a College by PK.
	 */
/*
 * public static void testFindByPK() { try { CollegeDTO bean = new CollegeDTO();
 * long pk = 12L; bean = model.findByPK(pk); if (bean == null) {
 * System.out.println("PK doesnt exist"); } System.out.println(bean.getId());
 * System.out.println(bean.getName()); System.out.println(bean.getAddress());
 * System.out.println(bean.getState()); System.out.println(bean.getCity());
 * System.out.println(bean.getPhoneNo());
 * System.out.println(bean.getCreatedBy());
 * System.out.println(bean.getCreatedDatetime());
 * System.out.println(bean.getModifiedBy());
 * System.out.println(bean.getModifiedDatetime()); } catch (ApplicationException
 * e) { e.printStackTrace(); } }
 * 
 *//**
	 * Tests find a College by Name.
	 */
/*
 * public static void testFindByName() {
 * 
 * try { CollegeDTO bean = model.findByName("LNCT"); if (bean == null) {
 * System.out.println("College Name doesnt Exist"); }
 * System.out.println(bean.getId()); System.out.println(bean.getName());
 * System.out.println(bean.getAddress()); System.out.println(bean.getState());
 * System.out.println(bean.getCity()); System.out.println(bean.getPhoneNo());
 * System.out.println(bean.getCreatedBy());
 * System.out.println(bean.getCreatedDatetime());
 * System.out.println(bean.getModifiedBy());
 * System.out.println(bean.getModifiedDatetime()); } catch (ApplicationException
 * e) { e.printStackTrace(); }
 * 
 * }
 * 
 *//**
	 * Tests add a College
	 * 
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
/*
 * public static void testAdd() throws DuplicateRecordException,
 * DatabaseException {
 * 
 * try { CollegeDTO bean = new CollegeDTO(); // bean.setId(2L);
 * //bean.setId(model.nextPK()); bean.setName("testt");
 * bean.setAddress("borawan"); bean.setState("mp"); bean.setCity("indore");
 * bean.setPhoneNo("073124244"); bean.setCreatedBy("Admin");
 * bean.setModifiedBy("Admin"); bean.setCreatedDatetime(new Timestamp(new
 * Date().getTime())); bean.setModifiedDatetime(new Timestamp(new
 * Date().getTime())); long pk = model.add(bean);
 * System.out.println("Test add success"); CollegeDTO addedBean =
 * model.findByPK(pk); if (addedBean == null) {
 * System.out.println("Test add fail"); } } catch (ApplicationException e) {
 * e.printStackTrace(); }
 * 
 * }
 * 
 *//**
	 * Tests update a College
	 */
/*
 * public static void testUpdate() {
 * 
 * try { CollegeDTO bean = model.findByPK(13L); bean.setName("change name");
 * bean.setAddress("vv"); model.update(bean);
 * System.out.println("Test Update success"); } catch (ApplicationException e) {
 * e.printStackTrace(); } catch (DuplicateRecordException e) {
 * e.printStackTrace(); }
 * 
 * }
 * 
 *//**
	 * Tests delete a College
	 */
/*
 * public static void testDelete() {
 * 
 * try { CollegeDTO bean = new CollegeDTO(); long pk = 13L; bean.setId(pk);
 * model.delete(bean); System.out.println("Test Delete success"); CollegeDTO
 * deletedBean = model.findByPK(pk); if (deletedBean != null) {
 * System.out.println("Test Delete Error"); } } catch (ApplicationException e) {
 * e.printStackTrace(); } }
 * 
 *//**
	 * Tests search a College by Name
	 */
/*
 * public static void testSearch() { try { CollegeDTO bean = new CollegeDTO();
 * List list = new ArrayList(); bean.setName("LNCT"); //
 * bean.setAddress("borawan"); list = model.search(bean, 1, 10); if (list.size()
 * < 0) { System.out.println("Test Search Error"); } Iterator it =
 * list.iterator(); while (it.hasNext()) { bean = (CollegeDTO) it.next();
 * System.out.println(bean.getId()); System.out.println(bean.getName());
 * System.out.println(bean.getAddress()); System.out.println(bean.getState());
 * System.out.println(bean.getCity()); System.out.println(bean.getPhoneNo());
 * System.out.println(bean.getCreatedBy());
 * System.out.println(bean.getCreatedDatetime());
 * System.out.println(bean.getModifiedBy());
 * System.out.println(bean.getModifiedDatetime()); } } catch
 * (ApplicationException e) { e.printStackTrace(); } }
 * 
 *//**
	 * Tests get List a College.
	 *//*
		 * public static void testList() {
		 * 
		 * try { CollegeDTO bean = new CollegeDTO(); List list = new ArrayList(); list =
		 * model.list(1, 10); if (list.size() < 0) {
		 * System.out.println("Test list fail"); } Iterator it = list.iterator(); while
		 * (it.hasNext()) { bean = (CollegeDTO) it.next();
		 * System.out.println(bean.getId()); System.out.println(bean.getName());
		 * System.out.println(bean.getAddress()); System.out.println(bean.getState());
		 * System.out.println(bean.getCity()); System.out.println(bean.getPhoneNo());
		 * System.out.println(bean.getCreatedBy());
		 * System.out.println(bean.getCreatedDatetime());
		 * System.out.println(bean.getModifiedBy());
		 * System.out.println(bean.getModifiedDatetime()); }
		 * 
		 * } catch (ApplicationException e) { e.printStackTrace(); } } }
		 */