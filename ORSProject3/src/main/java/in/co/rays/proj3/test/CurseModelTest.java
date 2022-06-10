package in.co.rays.proj3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;

public class CurseModelTest {

	public static CourseModelInt model = ModelFactory.getInstance().getCourseModel();

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		// testadd();
		// testupdate();
		// testDelete();
		// testFindByCourseName();
		// testFindByPk();
		// testSearch();
		// testList();

	}

	public static void testList() {
		try {
			CourseDTO Bean = new CourseDTO();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Bean = (CourseDTO) it.next();
				System.out.println(Bean.getCourseName());
				System.out.println(Bean.getId());
				System.out.println(Bean.getDescription());
				System.out.println(Bean.getCreatedBy());
				System.out.println(Bean.getModifiedBy());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() throws ApplicationException {
		List list = new ArrayList();
		CourseDTO Bean = null;
		CourseDTO bean = new CourseDTO();
		// bean.setId(6);
		bean.setCourseName("Bca");
		list = model.search(bean, 1, 10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Bean = (CourseDTO) it.next();
			System.out.println(Bean.getCourseName());
			System.out.println(Bean.getId());
			System.out.println(Bean.getDescription());
			System.out.println(Bean.getCreatedBy());
			System.out.println(Bean.getModifiedBy());
		}
		System.out.println("search completed");

	}

	public static void testFindByPk() throws ApplicationException {
		CourseDTO bean = new CourseDTO();
		bean = model.findByPK(3);
		System.out.println(bean != null);
		System.out.println(bean.getCourseName());
		System.out.println(bean.getId());
		System.out.println(bean.getDescription());

	}

	public static void testFindByCourseName() throws ApplicationException {
		CourseDTO dto = new CourseDTO();
		dto = model.findByCourseName("MCA");
		System.out.println(dto.getId());
		System.out.println(dto.getCourseName());
		dto.getCreatedBy();
	}

	public static void testDelete() throws ApplicationException {

		CourseDTO dto = new CourseDTO();
		dto.setId(4);
		model.delete(dto);
		System.out.println("succes delet");

	}

	public static void testupdate() throws ApplicationException, DuplicateRecordException {
		CourseDTO dto = new CourseDTO();
		dto.setId(2);
		dto.setCourseName("Om");
		// dto.setCreatedBy("root");
		dto.setDescription("root");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedBy("root");
		/*
		 * dto.setCreatedBy("ok"); dto.setModifiedBy("ok");
		 */

		model.update(dto);
		System.out.println("Update success");
	}

	public static void testadd() throws ApplicationException, DuplicateRecordException {

		CourseDTO dto = new CourseDTO();
		long i = 0;
		dto.setCourseName("M.com.s");
		dto.setDescription("Others");
		dto.setCreatedBy("root");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedBy("root");
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		i = model.add(dto);
		System.out.println("record added" + i);

	}

}
