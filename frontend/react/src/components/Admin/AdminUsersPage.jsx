import {useEffect, useState} from "react";
import {Button, Container, Pagination, Table} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import ConfirmationModal from "./ConfirmationModal";
import AdminService from "../../services/AdminService";
import CreateUpdateUserFormPage from "../../pages/CreateUpdateUserFormPage";
import CenteredSpinner from "../CenteredSpinner";
import InfoToast from "../InfoToast";
import {RoleEngToPl} from "../../enums/langTranslateEnums/roleEngToPl";

function AdminUsersPage(props) {
  const [users, setUsers] = useState(null);
  // const [totalUsers, setTotalUsers] = useState(null);
  const [totalPages, setTotalPages] = useState(null);
  const [currentPage, setCurrentPage] = useState(null);
  // const [isUpdate, setIsUpdate] = useState(false);
  const [userData, setUserData] = useState(null);

  const [showModal, setShowModal] = useState(false);
  const [chosenUserId, setChosenUserId] = useState(null);

  const [showToast, setShowToast] = useState(false);
  const [isActionSuccess, setIsActionSuccess] = useState(false);
  // const {page} = useParams();
  useEffect(() => {
    fetchPageData();
  }, []);

  function fetchPageData(page = 0) {
    props
      .makeRequest(page)
      .then((res) => {
        setUsers(res.data.users);
        // setTotalUsers(res.data.totalUsers);
        setTotalPages(res.data.totalPages);
        console.log(users);
        setCurrentPage(res.data.currentPage);
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  // const fetchRequest= useCallback(
  //     (number) => {
  //         fetchPageData(number)
  //     },
  //     []
  // );

  let navigate = useNavigate();

  function updateUserHandler(user) {
    setUserData(user);
  }
  function blockUserHandler(id) {
    setChosenUserId(id);
    setShowModal(true);
  }

  // console.log(users)
  if (!userData)
    return (
      <>
        {(!users || !totalPages || currentPage === null) && <CenteredSpinner />}

        <Container className="text-center mt-3">
          <Button
            onClick={() => {
              navigate("/create-user");
            }}
          >
            Dodaj użytkownika
          </Button>
          <Table className="my-3" striped bordered hover>
            <thead>
              <tr>
                <th>id</th>
                <th>Imię</th>
                <th>Nazwisko</th>
                <th>Nazwa użytkownika</th>
                <th>Email</th>
                <th>Number telefonu</th>
                <th>Rola</th>
                <th colSpan={2}>Akcje</th>
              </tr>
            </thead>

            <tbody>
              {users !== null &&
                users.map((user) => {
                  return (
                    <tr>
                      <td>{user.id}</td>
                      <td>{user.firstname}</td>
                      <td>{user.lastname}</td>
                      <td>{user.username}</td>
                      <td>{user.email}</td>
                      <td>{user.phone_number}</td>
                      <td>{RoleEngToPl[user.userRole]}</td>
                      <td>
                        <Button
                            onClick={() => {
                              updateUserHandler(user);
                            }}
                        >
                          Aktualizuj
                        </Button>
                      </td>
                      <td>
                        <Button
                          variant="secondary"
                          onClick={() => {
                            blockUserHandler(user.id);
                          }}
                        >
                          Zablokuj
                        </Button>
                      </td>
                    </tr>
                  );
                })}
            </tbody>
          </Table>

          {totalPages !== null &&
            currentPage !== null &&
            (() => {
              let active = currentPage + 1;
              let items = [];
              for (let number = 1; number <= totalPages; number++) {
                items.push(
                  <Pagination.Item
                    key={number}
                    onClick={() => {
                      fetchPageData(number - 1);
                      console.log(number);
                    }}
                    active={number === active}
                  >
                    {number}
                  </Pagination.Item>
                );
              }
              return (
                <div className="text-center">
                  <Pagination>{items}</Pagination>
                </div>
              );
            })()}
        </Container>
        {showModal === true && chosenUserId !== null && (
          <ConfirmationModal
            title="Blokowanie użytkownika"
            body="Czy zablokować użytkowanika?"
            footerButtonText="Zablokuj"
            chosenUserId={chosenUserId}
            show={showModal}
            onHide={() => setShowModal(false)}
            onBlock={(id) => {
              AdminService.blockUser(id)
                .then(() => {
                  setShowToast(true);
                  setIsActionSuccess(true);
                })
                .catch(() => {
                  setShowToast(true);
                  setIsActionSuccess(false);
                });
              setShowModal(false);
            }}
          />
        )}
        {showToast && (
          <InfoToast
            show={showToast}
            isActionSuccess={isActionSuccess}
            onClose={() => setShowToast(false)}
          />
        )}
      </>
    );

  if (userData)
    return (
      <CreateUpdateUserFormPage
        formData={userData}
        redirectUrl="/show-users"
        makeRequest={(jsonFormData, id) => {
          return AdminService.updateUser(jsonFormData, id);
        }}
        onNavigate={() => {
          setUserData(null);
          fetchPageData();
        }}
      />
    );
}

export default AdminUsersPage;
