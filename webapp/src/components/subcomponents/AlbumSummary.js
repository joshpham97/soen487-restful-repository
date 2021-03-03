import { makeStyles } from '@material-ui/core/styles';
import CloseRoundedIcon from '@material-ui/icons/CloseRounded';
import OpenInBrowserRoundedIcon from '@material-ui/icons/OpenInBrowserRounded';
import EditRoundedIcon from '@material-ui/icons/EditRounded';

const useStyles = makeStyles(() => ({
    header: {
        textAlign: 'center'
    },
    close: {
        position: 'absolute',
        color: 'white',
        top: '1pc',
        right: '1pc',
        '&:hover': {
            cursor: 'pointer'
        }
    },
    view: {
        position: 'absolute',
        top: '1pc',
        left: '1pc',
        cursor: 'pointer'
    },
    edit: {
        position: 'absolute',
        bottom: '1pc',
        right: '1pc',
        cursor: 'pointer'
    },
    isrc: {
        overflow: 'hidden',
        textOverflow: 'ellipsis',
        whiteSpace: 'nowrap'
    },
    releaseYear: {
        overflow: 'hidden',
        textOverflow: 'ellipsis',
        whiteSpace: 'nowrap'
    },
    description: {
        overflow: 'hidden',
        textOverflow: 'ellipsis',
        whiteSpace: 'nowrap',
        paddingRight: '2pc'
    }
}));

function AlbumSummary(props) {
    const classes = useStyles();

    const renderCloseIcon = () => {
        if(props.handleClose)
            return <CloseRoundedIcon className={classes.close} fontSize="large" onClick={props.handleClose} />;
    };

    const renderOpenInBrowserIcon = () => {
        if(props.handleOpenInBrowser)
            return <OpenInBrowserRoundedIcon className={classes.view} fontSize="large" onClick={props.handleOpenInBrowser} />;
    };

    const renderEditIcon = () => {
        if(props.handleEdit)
            return <EditRoundedIcon className={classes.edit} fontSize="large" onClick={props.handleEdit} />;
    };

    return (
      <div>
          <div className={classes.header}>
              <div className="font-weight-bold">
                  {props.title}
              </div>
              <div className="font-italic">
                  {props.firstname} {props.lastname}
              </div>
          </div>
          <div className={classes.isrc}>
              ISRC: {props.isrc}
          </div>
          <div className={classes.releaseYear}>
              Released: {props.releaseYear}
          </div>
          <div className={classes.description}>
              Description: {props.contentDesc}
          </div>

          {renderCloseIcon()}

          {renderOpenInBrowserIcon()}

          {renderEditIcon()}
      </div>
    );
}

export default AlbumSummary;