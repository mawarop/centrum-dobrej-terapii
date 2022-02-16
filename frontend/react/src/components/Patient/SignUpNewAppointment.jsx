import {useEffect} from "react";
import PatientService from "../../services/PatientService";
import UserInfoCard from "./UserInfoCard";
import {useState} from "react";
import {CardGroup, Col, Container, Row, Button} from "react-bootstrap";
import PickedDoctorFreeDates from "./PickedDoctorFreeDates";



function SignUpNewAppointment(props) {
    const [doctorsData, setDoctorsData] = useState(null);
    const [choosenDoctorEmail, setChoosenDoctorEmail] = useState(null);

    useEffect(() => {
        let doctorsPromise = PatientService.getDoctorsBaseData();
        doctorsPromise.then((res) =>
        {setDoctorsData(res.data);
            console.log(res.data);
            }
        ).catch((error) => console.log(error))
    }, [])

    return(
        <Container className="mx-auto">
            { choosenDoctorEmail === null &&
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
                choosenDoctorEmail !== null &&
                <div>
                <PickedDoctorFreeDates doctorEmail={choosenDoctorEmail} onBackClick={() => handleBackClick()}> </PickedDoctorFreeDates>
                </div>
            }


        </Container>

    )
    function handleCardButtonClick(index){
        setChoosenDoctorEmail(doctorsData[index].email);
    }
    function handleBackClick(){
        setChoosenDoctorEmail(null)
    }
}



export default SignUpNewAppointment;