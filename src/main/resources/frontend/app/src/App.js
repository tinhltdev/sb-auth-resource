import React, { useState } from 'react';
import Login from './Login'; // from step 1
import useAuthCheck from './useAuthCheck'; // from step 2

function App() {
  const { token, loading } = useAuthCheck();
  const [localToken, setLocalToken] = useState(null);

  // If silent login check returned a token, set it locally
  React.useEffect(() => {
    if (token) {
		console.log({token})
      setLocalToken(token);
    }
  }, [token]);

  if (loading) {
    return <div>Loading...</div>;
  }
  if (!localToken) {
    // Show login only on site A, on site B you may show a message or redirect
    return <Login onLogin={setLocalToken} />;
  }

  return (
    <div>
      <h1>Welcome! You are logged in.</h1>
      <p>Your token: {localToken}</p>
      {/* Call your API endpoints using this token */}
    </div>
  );
}

export default App;
