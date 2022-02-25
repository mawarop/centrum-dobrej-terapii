import {role} from "./role";
import {Nav} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
function NavbarLinks(props){
    const navigate = useNavigate();
    const patientLinks =[
    {name: "Wizyty pacjenta", href: "/patient-appointments"},
    {name: "Rejestracja na wizytę", href: "/sign-up-appointment"}
        ]
    ;
    const doctorLinks=[
        {name: "Wizyty doktora", href: "/doctor-appointments"},
        {name: "Prześlij dokumenty",href: "/upload-documents"},
        {name: "Pobierz dokumenty",href: "/download-documents"}
    ];
    const adminLinks =[
        {name: "Edytuj użytkowników", href: "/show-users"},
        {name: "Dodaj użytkownika", href: "/create-user"}]
    ;
    let links;

    switch (localStorage.getItem("participant-role")){
        case role.PATIENT: links = patientLinks;
        break;
        case role.DOCTOR: links = doctorLinks;
        break;
        case role.ADMIN: links = adminLinks;
        break;
    }
    let actualLocation = window.location.pathname;
        if(links && localStorage.getItem("participant-role") && actualLocation !== "/" && actualLocation !=="/login")
        return(links.map(link =>{
            return <Nav.Link onClick={() => navigate(link.href)} >{link.name}</Nav.Link>
        }));
        else
        {
            localStorage.clear();
            return(<Nav.Link onClick={() => navigate("/registration")}>Rejestracja pacjenta</Nav.Link>);
        }
}

export default NavbarLinks;