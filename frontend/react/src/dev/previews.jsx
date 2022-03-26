import React from "react";
import {ComponentPreview, Previews} from "@react-buddy/ide-toolbox";
import { PaletteTree } from "./palette";
import AdminAddAppointmentPage from "../components/admin/AdminAddAppointmentPage";

const ComponentPreviews = () => {
  return <Previews palette={<PaletteTree />}>
    <ComponentPreview
        path="/AdminAddAppointmentPage">
      <AdminAddAppointmentPage/>
    </ComponentPreview>
  </Previews>;
};

export default ComponentPreviews;
