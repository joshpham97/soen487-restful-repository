import { makeStyles } from '@material-ui/core/styles';
import CloseRoundedIcon from '@material-ui/icons/CloseRounded';
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
    edit: {
        position: 'absolute',
        bottom: '1pc',
        right: '1pc',
        '&:hover': {
            cursor: 'pointer'
        }
    }
}));

function AlbumDetails(props) {
    const classes = useStyles();

    const renderCloseIcon = () => {
        if(props.handleClose)
            return <CloseRoundedIcon className={classes.close} fontSize="large" onClick={props.handleClose} />;
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
          <div>
              ISRC: {props.isrc}
          </div>
          <div>
              Released: {props.releaseYear}
          </div>
          <div>
              Description: {props.contentDesc}
          </div>

          {renderCloseIcon()}

          {renderEditIcon()}
      </div>
    );
};

export default AlbumDetails;