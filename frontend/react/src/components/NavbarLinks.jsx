import {Role} from "../enums/role";
import {Nav} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

function NavbarLinks(props) {
  const navigate = useNavigate();
  const patientLinks = [
    {name: "Wizyty pacjenta", href: "/patient-appointments"},
    {name: "Rejestracja na wizytę", href: "/sign-up-appointment"},
  ];
  const doctorLinks = [
    {name: "Wizyty doktora", href: "/doctor-appointments"},
    {name: "Prześlij dokumenty", href: "/upload-documents"},
    {name: "Pobierz dokumenty", href: "/download-documents"},
  ];
  const adminLinks = [
    {name: "Edytuj użytkownika", href: "/show-users"},
    {name: "Dodaj użytkownika", href: "/create-user"},
    {name: "Dodaj wolny blok wizyty", href: "/add-appointment"},
  ];
  let links;

  switch (localStorage.getItem("participant-role")) {
    case Role.PATIENT:
      links = patientLinks;
      break;
    case Role.DOCTOR:
      links = doctorLinks;
      break;
    case Role.ADMIN:
      links = adminLinks;
      break;
  }
  let actualLocation = window.location.pathname;
  if (
      links &&
      localStorage.getItem("participant-role") &&
      actualLocation !== "/" &&
      actualLocation !== "/login"
  )
    return links.map((link) => {
      return (
          <Nav.Link
              onClick={() => {
                navigate(link.href);
                if (actualLocation === link.href) {
                  window.location.reload(false);
                }
              }}
          >
            {link.name}
          </Nav.Link>
      );
    });
  else {
    localStorage.clear();
    return (
        <Nav.Link
            onClick={() => {
              navigate("/registration");
              if (actualLocation === "/registration") {
                window.location.reload(false);
              }
            }}
        >
          Rejestracja pacjenta
        </Nav.Link>
    );
  }
}

export default NavbarLinks;
