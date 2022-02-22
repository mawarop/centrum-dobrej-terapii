import {useCallback, useEffect, useState} from "react";
import {APPOINTMENT_BG_COL, TEXT_COL} from "../../ConditionalEnums/FullCalendarEnum";
import {Pagination, Table, Container} from "react-bootstrap";
import {useParams} from "react-router-dom";



function AdminUsersPanel(props) {
    const [users, setUsers] = useState(null);
    const [totalUsers, setTotalUsers] = useState(null);
    const [totalPages, setTotalPages] = useState(null);
    const [currentPage, setCurrentPage] = useState(null);
    // const {page} = useParams();
    useEffect(()=>{
        fetchPageData();
    },[])

    function fetchPageData(page=0){
        props.makeRequest(page)
            .then((res) => {
                setUsers(res.data.users);
                setTotalUsers(res.data.totalUsers);
                setTotalPages(res.data.totalPages);
                console.log(users)
                setCurrentPage(res.data.currentPage);
            })
            .catch(function (error) {
                console.log(error);
            });
    }
    const fetchRequest= useCallback(
        (number) => {
            fetchPageData(number)
        },
        []
    );
    

    // console.log(users)
    return(
        <Container className="text-center">

            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Nazwa użytkownika</th>
                    <th>Email</th>
                    <th>Number telefonu</th>
                    <th>Rola</th>
                </tr>
                </thead>

                <tbody>
                {
                    users!== null && users.map((user) => {
                        return(
                            <tr>
                                <td>{user.id}</td>
                                <td>{user.firstname}</td>
                                <td>{user.lastname}</td>
                                <td>{user.username}</td>
                                <td>{user.email}</td>
                                <td>{user.phone_number}</td>
                                <td>{user.userRole}</td>
                            </tr>
                        );
                    })
                }
                </tbody>

            </Table>

            {
                totalPages!==null && currentPage!==null && (()=>{
                    let active = currentPage + 1;
                    let items = [];
                    for (let number = 1; number <= totalPages; number++) {
                        items.push(
                            <Pagination.Item key={number} onClick={() =>{fetchPageData(number-1);
                            console.log(number)}} active={number === active}>
                                {number}
                            </Pagination.Item>,
                        );
                    }
                    return <div className="text-center"><Pagination>{items}</Pagination></div>

                })()
            }

            {/*<Row className="rounded bg-primary mt-2"  >*/}
            {/*<Col>Imię</Col>*/}
            {/*<Col>Nazwisko</Col>*/}
            {/*<Col>Nazwa użytkownika</Col>*/}
            {/*<Col>Email</Col>*/}
            {/*<Col>Numer telefonu</Col>*/}
            {/*<Col>Rola</Col>*/}
            {/*</Row>*/}


            {/*{*/}
            {/*    users!== null && users.map((user) => {*/}
            {/*        return(*/}
            {/*            <Row className="mt-2">*/}
            {/*                <Col>{user.firstname}</Col>*/}
            {/*                <Col>{user.lastname}</Col>*/}
            {/*                <Col>{user.username}</Col>*/}
            {/*                <Col>{user.email}</Col>*/}
            {/*                <Col>{user.phone_number}</Col>*/}
            {/*                <Col>{user.userRole}</Col>*/}
            {/*            </Row>*/}
            {/*        );*/}
            {/*    })*/}
            {/*}*/}

        </Container>

    )
    
}

export default AdminUsersPanel;