const API_BASE_URL = 'http://localhost:8080/api';

export async function login(username, password) {
  const res = await fetch(`${API_BASE_URL}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
    credentials: 'include', // if backend uses cookies, else remove
  });
  if (!res.ok) {
    const error = await res.text();
    throw new Error(error);
  }
  return res.json(); // expect { token: '...' }
}

export async function fetchProtectedMessage(token) {
  const res = await fetch(`${API_BASE_URL}/protected/message`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  if (!res.ok) {
    throw new Error('Unauthorized or other error');
  }
  return res.json(); // expect { message: '...' }
}
