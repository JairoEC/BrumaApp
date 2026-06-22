import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Buscamos el token JWT guardado en el LocalStorage
  const token = localStorage.getItem('token');

  // Si el token existe, clonamos la petición y le inyectamos la cabecera Authorization
  if (token) {
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(clonedReq);
  }

  // Si no hay token (como en el login/registro), la petición sigue su curso normal
  return next(req);
};
