import React, { Component } from "react";
import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";
class RequireAuth extends Component {
  render() {
    if (sessionStorage.getItem("logged-in") === "true") {
      return this.props.children;
    } else {
      return <Navigate to="/login" />;
    }
  }
}

// PrivateRoute.propTypes = {
//   path: PropTypes.string,
//   element: PropTypes.element,
export default RequireAuth;
