package in.co.rays.proj3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelHibImpl;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.model.ModelFactory;

public class CollegeModelTest {
	public static CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
	public static CollegeModelHibImpl model2 = (CollegeModelHibImpl) ModelFactory.getInstance().getCollegeModel();

	public static void main(String[] args) throws DuplicateRecordException {
		 testadd();
		// testDelete();
		// testUpdate();
		// testsearch();
		// testlist();
		// testFindbypk();
		
		// testFindbyName();
	}
	public static void testadd() throws DuplicateRecordException {
		try {
			CollegeDTO dto = new CollegeDTO();
			dto.setName("JJ public");
			dto.setState("GJ");
			dto.setAddress("Sanjana Park");
			dto.setCity("Ujjain");
			dto.setPhoneNo("980987899");
			dto.setCreatedBy("Admin");
			dto.setModifiedBy("Admin");
			dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
			dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(dto);
			System.out.println("Test add success");
			CollegeDTO addedBean = model.findByPK(pk);
			if (addedBean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindbyName() {
		try {
			CollegeDTO bean = model.findByName("Patel College");
			if (bean == null) {
				System.out.println("College Name doesnt Exist");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindbypk() {
		try {
			CollegeDTO bean = new CollegeDTO();
			long pk = 1L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("PK doesnt exist");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testlist() {
		try {
			CollegeDTO bean = new CollegeDTO();
			List list = new ArrayList();
			list = model.list(0, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeDTO) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testsearch() {
		try {
			CollegeDTO bean = new CollegeDTO();
			List list = new ArrayList();
			bean.setName("Patel College");
			// bean.setAddress("borawan");
			list = model.search(bean, 0, 10);
			if (list.size() < 0) {
				System.out.println("Test Search Error");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeDTO) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() {
		try {
			CollegeDTO bean = model.findByPK(4L);
			bean.setName("change name");
			bean.setAddress("vv");
			model.update(bean);
			System.out.println("Test Update success");
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {
		try {
			CollegeDTO bean = new CollegeDTO();
			long pk = 4L;
			bean.setId(pk);

			CollegeDTO deletedBean = model2.findByPK(pk);

			if (deletedBean != null) {
				model2.delete(bean);
				System.out.println("Test Delete success");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	

}
