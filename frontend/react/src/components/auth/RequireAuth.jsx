import React, { Component, useContext } from "react";
import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";
function RequireAuth(props) {
  if (localStorage.getItem("logged-in") === "true") {
    return props.children;
  } else {
    return <Navigate to="/login" />;
  }
}

// PrivateRoute.propTypes = {
//   path: PropTypes.string,
//   element: PropTypes.element,
export default RequireAuth;
