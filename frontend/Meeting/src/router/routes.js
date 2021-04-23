import Home from "../views/Home.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/session/:id",
    name: "Session",
    component: () => import("../views/Session.vue")
  }
];
export default routes;
