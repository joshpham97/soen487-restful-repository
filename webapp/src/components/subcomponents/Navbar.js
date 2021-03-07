import React from 'react';
import { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import MenuIcon from '@material-ui/icons/Menu';

const links = [
    {
        label: 'Home',
        path: '/'
    }, {
        label: 'Example1',
        path: '/example'

    }, {
        label: 'Example2',
        path: '/example2'
    }, {
        label: 'Albums',
        path: '/albums'
    }
];

const useStyles = makeStyles(() => ({
    navbar: {
        backgroundColor: '#262626',
        padding: '1em',
        marginBottom: '1.5pc',
        textAlign: 'center'
    },
    navElement: {
        color: '#E9E9E9',
        fontWeight: 'bold',
        '&:not(:first-child)': {
            marginLeft: '2em'
        }
    },
    navLink: {
        color: '#E9E9E9',
        fontWeight: 'bold',
        borderBottom: '2px solid transparent',
        transition: 'border-bottom-color 0.25s',
        '&:hover': {
            color: '#E9E9E9',
            textDecoration: 'none',
            borderBottomColor: 'white'
        }
    },
    menuContainer: {
        display: 'inline-block'
    },
    menuIcon: {
        color: '#E9E9E9',
        cursor: 'pointer',
        position: 'relative'
    },
    menuOpen: {
        visibility: 'visible',
        opacity: 100
    },
    menuClose: {
        visibility: 'hidden',
        opacity: 0
    },
    menu: {
        zIndex: 1000,
        backgroundColor: 'white',
        position: 'absolute',
        borderRadius: '3px',
        transition: 'visibility 0.25s, opacity 0.25s',
        boxShadow:
            '0px 8px 10px 1px rgba(0, 0, 0, 0.14),' +
            '0px 3px 14px 2px rgba(0, 0, 0,0.12)'
    },
    menuElement: {
        padding: '5px 10px',
        transition: 'background-color 0.25s',
        '&:hover' : {
            backgroundColor: 'rgba(0, 0, 0, 0.1)'
        }
    },
    menuLink: {
        color: 'black',
        '&:hover': {
            textDecoration: 'none',
            color: 'black'
        }
    }
}));

function Navbar(props) {
    const classes = useStyles();

    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const [openMenu, setOpenMenu] = useState(false);

    const header = props.header ? props.header : 'A2';
    const minWidth = links.length * 125;

    window.onresize = () => setWindowWidth(window.innerWidth);
    window.onclick = () => setOpenMenu(false);

    const toggleMenu = (e) => {
        e.stopPropagation();
        setOpenMenu(!openMenu);
    };

    const getMenuClasses = () => {
        return classes.menu + ' ' + (openMenu ? classes.menuOpen : classes.menuClose);
    };

    const renderNavLinks = () => {
        if(windowWidth < minWidth) {
            return (
                <React.Fragment>
                    <div className={classes.menuContainer}>
                        <MenuIcon className={classes.menuIcon} onClick={(e) => toggleMenu(e)} />

                        <div className={getMenuClasses()}>
                            {links.map((link, i) => (
                                <a key={i}  className={classes.menuLink} href={link.path} onClick={() => setOpenMenu(false)}>
                                    <div className={classes.menuElement}>
                                        {link.label}
                                    </div>
                                </a>
                            ))}
                        </div>
                    </div>

                    <span className={classes.navElement}>
                        {header}
                    </span>
                </React.Fragment>
            );
        }

        return links.map((link, i) => (
            <span key={i} className={classes.navElement}>
                <a className={classes.navLink} href={link.path}>{link.label}</a>
            </span>
        ));
    };

    return (
      <div id="navbar" className={classes.navbar}>
          {renderNavLinks()}
      </div>
    );
}

export default Navbar;
