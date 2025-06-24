// components/Navbar.js
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Home, Library, User, Menu, Plus } from 'lucide-react';
import '../styles/Navbar.css';

const iconMap = {
  home: <Home size={24} />,
  library: <Library size={24} />,
  user: <User size={24} />,
  plus: <Plus size={24} />
};

const Navbar = ({ isLoggedIn, onLogout, menuItems, solde }) => {
  const [expanded, setExpanded] = useState(false);
  const [hoveredItem, setHoveredItem] = useState(null);

  const handleMouseEnter = () => setExpanded(true);
  const handleMouseLeave = () => {
    setExpanded(false);
    setHoveredItem(null);
  };

  return (
    <nav className={`navbar ${expanded ? 'expanded' : 'collapsed'}`} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
      <div className="navbar-logo">
        <Link to="/">
          <Menu size={24} />
          {expanded && <span className="logo-text">MS Store</span>}
        </Link>
      </div>

      <ul className="navbar-menu">
        {menuItems.map((item, index) => (
          <li
            key={index}
            className={`navbar-menu-item ${hoveredItem === index ? 'hovered' : ''}`}
            onMouseEnter={() => setHoveredItem(index)}
            onMouseLeave={() => setHoveredItem(null)}
          >
            <Link to={item.path}>
              <div className="menu-icon">{iconMap[item.icon]}</div>
              {expanded && <span className="menu-label">{item.label}</span>}
            </Link>
          </li>
        ))}
      </ul>
        {console.log(solde)}
      {isLoggedIn && expanded && (
        <div className="navbar-footer">
          <p>Solde : {solde} € </p>
          <br />
          <button className="logout-btn" onClick={onLogout}>
            Déconnexion
          </button>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
