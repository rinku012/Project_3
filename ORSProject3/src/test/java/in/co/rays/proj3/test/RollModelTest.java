package in.co.rays.proj3.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;

/**
 * Role Model Test classes
 * 
 * @author PAWAN_TECHNIQUES
 *
 */
public class RollModelTest {
	
	/**
	 * Model object to test	
	 */
	public static RoleModelInt model = ModelFactory.getInstance().getRoleModel();

    /**
     * Main method to call test methods.
     * 
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        // testAdd();
        // testDelete();
        // testUpdate();
        // testFindByPK();
        // testFindByName();
        // testSearch();
        // testList();
    }

    /**
     * Tests add a Role
     */
    public static void testAdd() {

        try {
            RoleDTO bean = new RoleDTO();
            // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            // bean.setId(1L);
            bean.setName("shivam");
            bean.setDescription("kumar");
            long pk = model.add(bean);
            RoleDTO addedbean = model.findByPk(pk);
            if (addedbean == null) {
                System.out.println("Test add fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests delete a Role
     */
    public static void testDelete() {

        try {
            RoleDTO bean = new RoleDTO();
            long pk = 13L;
            bean.setId(pk);
            model.delete(bean);
            RoleDTO deletedbean = model.findByPk(pk);
            System.out.println("Delete Method");
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests update a Role
     */
    public static void testUpdate() {

        try {
            RoleDTO bean = model.findByPk(13L);
            bean.setName("13");
            bean.setDescription("Ejjjjjjjjng");
            model.update(bean);
            System.out.println("update Method");

            RoleDTO updatedbean = model.findByPk(6L);
            if (!"12".equals(updatedbean.getName())) {
                System.out.println("Test Update fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests find a User by PK.
     */
    public static void testFindByPK() {
        try {
            RoleDTO bean = new RoleDTO();
            long pk = 12L;
            bean = model.findByPk(pk);
            System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getDescription());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests find a User by Name.
     */
    public static void testFindByName() {
        try {
            RoleDTO bean = new RoleDTO();
            bean = model.findByRoleName("admin");
            if (bean == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getDescription());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests get Search
     */
    public static void testSearch() {

        try {
            RoleDTO bean = new RoleDTO();
            List list = new ArrayList();
            bean.setName("student");
            list = model.search(bean, 0, 0);
            if (list.size() < 0) {
                System.out.println("Test Serach fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (RoleDTO) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getName());
                System.out.println(bean.getDescription());
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests get List.
     */
    public static void testList() {

        try {
            RoleDTO bean = new RoleDTO();
            List list = new ArrayList();
            list = model.list(1, 10);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (RoleDTO) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getName());
                System.out.println(bean.getDescription());
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}



