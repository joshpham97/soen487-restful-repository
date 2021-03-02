import { makeStyles, withStyles } from '@material-ui/core/styles';
import MuiCard from '@material-ui/core/Card';
import { CardActionArea, CardMedia, CardHeader } from '@material-ui/core';

const useStyles = makeStyles(() => ({
    title: {
        fontWeight: 'bold'
    },
    artist: {
        fontStyle: 'italic'
    }
}));

const Card = withStyles({
    root: {
        width: '25%',
        minWidth: '200px',
        maxWidth: '300px',
        margin: 0,
        border: '1px solid rgba(0, 0, 0, 0.2)',
        boxShadow: 'none'
    }
})(MuiCard);

function AlbumCover(props) {
    const classes = useStyles();

    return (
      <Card>
          <CardActionArea>
              <CardMedia>
                  MEDIA
              </CardMedia>
              <CardHeader title={props.title} subheader={props.firstname + " " + props.lastname} />
          </CardActionArea>
      </Card>
    );
}

export default AlbumCover;
