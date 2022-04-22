// export function getAxiosJwtConfig() {
//   return {
//     headers: {
//       Accept: "application/json",
//       "Content-Type": "application/json",
//       Authorization: sessionStorage.getItem("jwt-token"),
//     },
//   };
// }
const axiosJwtConfig = () => {
  return {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: sessionStorage.getItem("jwt-token"),
    },
  };
};
export default axiosJwtConfig;
