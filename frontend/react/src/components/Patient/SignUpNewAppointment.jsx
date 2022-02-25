import {useEffect} from "react";
import PatientService from "../../services/PatientService";
import UserInfoCard from "./UserInfoCard";
import {useState} from "react";
import {CardGroup, Col, Container, Row, Button} from "react-bootstrap";
import UserAppointments from "../UserAppointments";
import {role} from "../../role";



function SignUpNewAppointment(props) {
    const [doctorsData, setDoctorsData] = useState(null);
    const [chosenDoctorEmail, setChosenDoctorEmail] = useState(null);

    useEffect(() => {
        let doctorsPromise = PatientService.getDoctorsBaseData();
        doctorsPromise.then((res) =>
        {setDoctorsData(res.data);
            // console.log(res.data);
            }
        ).catch((error) => console.log(error))
    }, [])

    return(
        <Container className="mx-auto">
            { chosenDoctorEmail === null &&
                <Row xs={1} md={2} xl={3} className=" mt-2">
                    {/*<CardGroup>*/}
                    {(doctorsData !== null) &&

                        (doctorsData.map((doctorData, index) => {
                            return <Col><UserInfoCard key={index} {...doctorData} onClick={() => handleCardButtonClick(index)}> </UserInfoCard></Col>
                        }))}
                    {/*</CardGroup>*/}
                </Row>
            }
            {
                chosenDoctorEmail !== null &&
                <div>
                <UserAppointments role={role.PATIENT}  makeRequest={() => {return PatientService.getDoctorFreeDates(chosenDoctorEmail)}} onBackClick={() => handleBackClick()}> </UserAppointments>
                </div>
            }

        </Container>

    )
    function handleCardButtonClick(index){
        setChosenDoctorEmail(doctorsData[index].email);
    }
    function handleBackClick(){
        setChosenDoctorEmail(null)
    }
}



export default SignUpNewAppointment;