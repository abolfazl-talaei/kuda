export const ROUTES = {
  home: {
    name: 'home',
    route: '/',
  },
  newkuda: {
    name: 'new.kuda',
    route: 'newkuda',
  },
  listkuda: {
    name: 'list.kuda',
    route: 'kudas',
  },
  listwinner: {
    name: 'list.winner',
    route: 'winners',
  },
  newuser: {
    name: 'new.user',
    route: 'newuser',
  },
  listuser: {
    name: 'list.user',
    route: 'users',
  },
  settings: {
    name: 'settings',
    route: 'settings',
  },
};

export const getCurrentRoute = () => {
  if (typeof window === 'undefined') {
    return '';
  }
  return window.location.pathname;
};

export const setNewRoute = (newRoute) => {
  if (typeof window === 'undefined') {
    return '';
  }
  window.location.href = newRoute;
};

export const getRouteConfig = (key) => {
  if (typeof window === 'undefined') {
    return {};
  }
  return ROUTES[key];
};

export const getCurrentRouteConfig = () => {
  const route = getCurrentRoute();
  if (route === '/') {
    return ROUTES['home'];
  }
  for (let routeInfo in ROUTES) {
    if (ROUTES[routeInfo].route === route.substring(1)) {
      return ROUTES[routeInfo];
    }
  }
  return {};
};
