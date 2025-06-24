import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login({ onLogin }) {
  const [credentials, setCredentials] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const response = await fetch('http://localhost:8080/api/utilisateurs/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(credentials),
      });

      if (!response.ok) throw new Error('Échec de la connexion');

      const userData = await response.json();
      onLogin(userData); // déclenche le setUser dans App.js
      navigate('/'); // Redirection vers la page d’accueil
    } catch (err) {
      setError("Email ou mot de passe incorrect.");
    }
  };

  return (
    <div className="login-form text-center mt-5">
      <h2 className="mb-4 text-primary">Connexion</h2>
      <form className='profile-form' onSubmit={handleSubmit}>
        <div className="form-group">
          <div className="mb-3">
            <label>Email :</label><br />
            <input
              type="email"
              name="email"
              value={credentials.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-3">
            <label>Mot de passe :</label><br />
            <input
              type="password"
              name="password"
              value={credentials.password}
              onChange={handleChange}
              required
            />
          </div>

          {error && <div className="error-message">{error}</div>}

          <button type="submit" className="mt-3">Se connecter</button>
        </div>
      </form>
    </div>
  );
}

export default Login;
