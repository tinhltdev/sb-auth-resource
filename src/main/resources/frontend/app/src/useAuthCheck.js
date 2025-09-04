import React, { useEffect, useState } from 'react';

export default function useAuthCheck() {
  const [token, setToken] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkStatus = async () => {
      try {
        const res = await fetch('http://localhost:8080/auth/status', {
          method: 'GET',
          credentials: 'include', // send session cookie to backend
        });

        if (res.ok) {
          const data = await res.json();
          setToken(data.access_token);
        }
      } catch (err) {
        console.error('Auth check failed', err);
      } finally {
        setLoading(false);
      }
    };

    checkStatus();
  }, []);

  return { token, loading };
}
