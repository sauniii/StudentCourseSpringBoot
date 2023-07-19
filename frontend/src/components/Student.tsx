import React, { useEffect, useState } from "react";
import axios from "axios";
import Modal from "react-modal";
import * as yup from "yup";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";

import "../CSS/modal.css";

const studentSchema = yup.object().shape({
  name:yup.string().required("Name is required"),
  email:yup.string().email("Invalid email").required("Email is required"),
  dob:yup.date().required("Date of Birth is required") .typeError("Invalid date format"),
  
});

interface Student {
  id: number;
  name: string;
  email: string;
  dob: Date;
  age: number;
}

type StudentFormData = {
  name: string;
  email: string;
  dob: Date;
};

export default function Student() {
  const [students, setStudents] = useState<Student[]>([]);

  /*   const [update, setUpdated] = useState<Student | null>(null); */

  //insert modal
  const [isOpen, setIsOpen] = useState(false);

  const [formValues, setFormValues] = useState<Student>({
    id: 0,
    name: "",
    email: "",
    dob: new Date(),
    age: 0,
  });

  Modal.setAppElement("#root");

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(studentSchema),
  });

  const handleCloseModal = () => {
    setIsOpen(false);
    setIsOpenUpdate(false);
    setIsOpenView(false);
  };


  const handleSubmitForm = async (data: StudentFormData) => {
    console.log(data);
    try {
      console.log("We are here");
      await axios.post("http://localhost:8080/api/v1/student", data);
      handleCloseModal();
       window.location.reload();
    } catch (error) {
      console.error("Error saving student data:", error);
    }
  };

  useEffect(() => {
    getAll();
  }, []);

  const getAll = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/v1/student");
      const studentsWithDate = response.data.map((student: Student) => ({
        ...student,
        dob: new Date(student.dob), // Convert dob to a Date object
      }));
      setStudents(studentsWithDate);
    } catch (error) {
      console.error("Error fetching student data:", error);
      setStudents([]);
    }
  };

  function handleAddStudent(): void {
    setIsOpen(true);
  }

  function handleDelete(id: number): void {
    axios
      .delete(`http://localhost:8080/api/v1/student/${id}`)
      .then((response) => {
        console.log("Deleted successfully");
        getAll();
      })
      .catch((error) => {
        console.error("Error deleting student:", error);
      });
  }

  //update modal
  const [isOpenUpdate, setIsOpenUpdate] = useState(false);

  const [updateFormValues, setUpdateFormValues] = useState<Student>({
    id: 0,
    name: "",
    email: "",
    dob: new Date(),
    age: 0,
  });

  function handleUpdate(student: Student): void {
    setUpdateFormValues(student);
    setIsOpenUpdate(true);
  }

  async function updateStudent(updateFormValues: Student) {
    console.log("We are here");
    try {
      // Include the email and name as query parameters in the API URL
      const apiUrl = `http://localhost:8080/api/v1/student/${
        updateFormValues.id
      }?email=${updateFormValues.email}&name=${updateFormValues.name}&dob=${
        updateFormValues.dob.toISOString().split("T")[0]
      }`;

      // Remove the email and name properties from the request body
      const { email, name, ...updateData } = updateFormValues;

      // Make the PUT request with the modified URL and request body
      await axios.put(apiUrl, updateData);

      // Update the dob field in the updateFormValues state
      setUpdateFormValues((prevValues) => ({
        ...prevValues,
        dob: updateFormValues.dob,
      }));

      console.log("Student updated successfully");
      getAll();
      handleCloseModal();
    } catch (error) {
      console.error("Error updating student:", error);
    }
  }

  const handleUpdateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    const { name, value } = e.target;
    setUpdateFormValues((prevValues) => ({
      ...prevValues,
      [name]: name === "dob" ? new Date(value) : value,
    }));
  };

  const [isOpenView, setIsOpenView] = useState(false);

  const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);

  function handleView(id: number): void {
    setIsOpenView(true);
    axios.get(`http://localhost:8080/api/v1/student/${id}`).then((response) => {
      console.log("Displayed successfully");
      setSelectedStudent(response.data);
    });
  }

  // const [validationErrors, setValidationErrors] = useState<{ [key: string]: string }>({});
  const onError = (errors: any) => {
    console.log(errors);
  };

  return (
    <>
      <div className="text-center mb-10 mt-5">
        <h1>Students</h1>
      </div>
      <button
        className="mb-4 float-right bg-purple-800 hover:bg-purple-900 text-white font-bold py-2 px-4 rounded"
        onClick={handleAddStudent}
      >
        Add Student
      </button>
      <table className="table">
        <thead>
          <tr>
            <th></th>
            <th>Name</th>
            <th>Email</th>
            <th>DOB</th>
            <th>Age</th>
          </tr>
        </thead>
        <tbody>
          {students.map((student) => (
            <tr key={student.id}>
              <td>{student.id}</td>
              <td>{student.name}</td>
              <td>{student.email}</td>
              <td>{student.dob.toLocaleDateString()}</td>
              <td>{student.age}</td>
              <td>
                <button
                  className="w-full bg-red-300 border border-gray-300"
                  onClick={() => handleView(student.id)}
                >
                  View
                </button>
              </td>
              <td>
                <button
                  className="w-full bg-red-300 border border-gray-300"
                  //    onClick={() => handleView(student.id)}
                >
                  Add Course
                </button>
              </td>
              <td>
                <button
                  className="w-full border border-gray-300"
                  onClick={() => handleUpdate(student)}
                >
                  Update
                </button>
              </td>
              <td>
                <button
                  className="w-full bg-red-300 border border-gray-300"
                  onClick={() => handleDelete(student.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/*     ---------------------  Insert Student modal with form ---------------------------  */}

      <Modal
        isOpen={isOpen}
        onRequestClose={handleCloseModal}
        className="react-modal"
      >
        <form onSubmit={handleSubmit(handleSubmitForm, onError)} className="p-4">
          <h3>Add Student</h3>
          <hr />
          <br />
          <div className="mb-4">
            <label
              htmlFor="name"
              className="block text-gray-700 font-bold mb-2 "
            >
              Enter Name:
            </label>
            <input
            id="name" 
              type="text"
              className="w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-purple-500"
              {...register("name")}
            />

            <p>{errors.name?.message}</p>
          </div>

          <div className="mb-4">
            <label
              htmlFor="email"
              className="block text-gray-700 font-bold mb-2"
            >
              Enter Email:
            </label>
            <input
              type="email"
              id="email"
              {...register("email")}
              className="w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-purple-500"
            />
            <p>{errors.email?.message}</p>
          </div>

          <div className="mb-4">
            <label htmlFor="dob" className="block text-gray-700 font-bold mb-2">
              Enter Date of Birth:
            </label>
            <input
              type="date"
              id="dob"
          /*     value={formValues.dob.toISOString().split("T")[0]} */
              {...register("dob")}
              className="w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-purple-500"
            />
          </div>
          <p>{errors.dob?.message}</p>
          <button
            type="submit"
            className="float-right bg-purple-800 hover:bg-purple-900 text-white font-bold py-2 px-4 rounded"
          >
            Submit 
          </button>

          <button
            type="button"
            className="float-right mr-4 border border-purple-800 hover:bg-purple-800 hover:text-white text-purple-800 font-bold py-2 px-4 rounded"
            onClick={handleCloseModal}
          >
            Cancel
          </button>
        </form>
      </Modal>

      {/*     ---------------------  Update Student modal with form ---------------------------  */}

      <Modal
        isOpen={isOpenUpdate}
        onRequestClose={handleCloseModal}
        className="react-modal"
      >
        <form className="p-4" onSubmit={() => updateStudent(updateFormValues)}>
          <h3>Update Student</h3>
          <hr />
          <br />

          <div className="mb-4">
            <label
              htmlFor="name"
              className="block text-gray-700 font-bold mb-2 "
            >
              Enter Name:
            </label>
            <input
              type="text"
              id="name"
              name="name"
              value={updateFormValues?.name}
              onChange={handleUpdateChange}
              className="w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-purple-500"
            />
          </div>

          <div className="mb-4">
            <label
              htmlFor="email"
              className="block text-gray-700 font-bold mb-2"
            >
              Enter Email:
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={updateFormValues?.email}
              onChange={handleUpdateChange}
              className="w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-purple-500"
            />
          </div>

          <div className="mb-4">
            <label htmlFor="dob" className="block text-gray-700 font-bold mb-2">
              Enter Date of Birth:
            </label>
            <input
              type="date"
              id="dob"
              name="dob"
              value={updateFormValues?.dob.toISOString().split("T")[0]}
              onChange={handleUpdateChange}
              className="w-full border border-gray-300 px-3 py-2 rounded-md focus:outline-none focus:border-purple-500"
            />
          </div>

          <button
            type="submit"
            className="float-right bg-purple-800 hover:bg-purple-900 text-white font-bold py-2 px-4 rounded"
          >
            Update
          </button>

          <button
            type="button"
            className="float-right mr-4 border border-purple-800 hover:bg-purple-800 hover:text-white text-purple-800 font-bold py-2 px-4 rounded"
            onClick={handleCloseModal}
          >
            Cancel
          </button>
        </form>
      </Modal>

      {/*     ---------------------  View Student modal with form ---------------------------  */}

      <Modal
        isOpen={isOpenView}
        onRequestClose={handleCloseModal}
        className="react-modal"
      >
        {selectedStudent ? ( // Render if selectedStudent is not null
          <>
            <table className="table" style={{ fontWeight: "25px" }}>
              <tr>
                <td>Name:</td>
                <td>{selectedStudent.name}</td>
              </tr>
              <br />
              <tr>
                <td>Email:</td>
                <td>{selectedStudent.email}</td>
              </tr>
              <br />
              <tr>
                <td>Date of Birth: </td>
                <td>{new Date(selectedStudent.dob).toLocaleDateString()}</td>
              </tr>
              <br />
              <tr>
                <td>Age:</td> <td>{selectedStudent.age}</td>
              </tr>
              <br />
              <br />

              <br />

              <tr>
                <td></td>
                <td>
                  <button
                    type="button"
                    className="mb-0 float-right bg-purple-800 font-bold py-2 px-4 rounded"
                    onClick={handleCloseModal}
                  >
                    Close
                  </button>
                </td>
              </tr>
            </table>
          </>
        ) : (
          // Render loading or no data message if selectedStudent is null
          <div>Loading...</div>
        )}
      </Modal>
    </>
  );
}
