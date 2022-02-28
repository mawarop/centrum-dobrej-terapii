import React, { Component } from "react";
import { Navbar, Nav, Container, NavDropdown, Button } from "react-bootstrap";
import UserService from "../services/UserService";
import {useNavigate} from "react-router-dom";
import NavbarLinks from "../NavbarLinks";
import userMainPath from "../utilities/pagePath/userMainPath";
function MainNavbar(props){
    let navigate = useNavigate();
    return (
      <Navbar bg="primary" expand="lg" variant="dark">
        {/* <Container> */}
        <Navbar.Brand onClick={() => navigate(userMainPath.getPath())} href="#">Centrum dobrej terapii</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">

            {/*<Nav.Link href="#home">Home</Nav.Link>*/}
            {/*<Nav.Link href="#link">Link</Nav.Link>*/}
              <NavbarLinks></NavbarLinks>
            {/*<NavDropdown title="Dropdown" id="basic-nav-dropdown">*/}
            {/*  <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>*/}
            {/*  <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>*/}
            {/*  <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>*/}
            {/*  <NavDropdown.Divider />*/}
            {/*  <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>*/}
            {/*</NavDropdown>*/}
          </Nav>
            <Nav>
                <Button onClick={() => {
                    UserService.logout();
                    navigate("/login");
                }} variant="outline-light">Wyloguj</Button>
            </Nav>
        </Navbar.Collapse>
        {/* </Container> */}
      </Navbar>
    );
}

export default MainNavbar;
