import { Link } from "react-router-dom";
import { useState } from "react";
import { FaHome, FaGamepad, FaUser, FaSignInAlt } from "react-icons/fa";
import { useAuth } from "../context/AuthContext";

const menuItems = [
  { to: "/", icon: <FaHome />, label: "Accueil" },
  { to: "/my-games", icon: <FaGamepad />, label: "Mes Jeux" },
  { to: "/account", icon: <FaUser />, label: "Mon Compte" },
];

export default function Sidebar() {
  const [expanded, setExpanded] = useState(false);
  const { user } = useAuth();

  return (
    <div
      onMouseEnter={() => setExpanded(true)}
      onMouseLeave={() => setExpanded(false)}
      className="bg-gray-800 text-white h-screen w-16 hover:w-48 transition-all duration-200 fixed top-0 left-0 flex flex-col items-center py-4 space-y-4 z-50"
    >
      {menuItems.map((item, index) => (
        <Link
          key={index}
          to={item.to}
          className="flex items-center space-x-2 hover:bg-gray-700 w-full px-4 py-2 rounded"
        >
          <span>{item.icon}</span>
          {expanded && <span>{item.label}</span>}
        </Link>
      ))}
      {!user && (
        <Link to="/login" className="flex items-center space-x-2 hover:bg-gray-700 w-full px-4 py-2 rounded">
          <FaSignInAlt />
          {expanded && <span>Connexion</span>}
        </Link>
      )}
    </div>
  );
}
