package department;

import java.util.Optional;
import java.util.OptionalInt;

public interface IDepartmentRepository {

	public Optional<Department> findDepartmentbyName(String departmentName) throws Exception;

	public Optional<Department> findDepartmentById(int id) throws Exception;
}