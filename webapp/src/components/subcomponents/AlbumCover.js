import { withStyles } from '@material-ui/core/styles';
import MuiCard from '@material-ui/core/Card';
import MuiCardMedia from '@material-ui/core/CardMedia';
import MuiCardHeader from '@material-ui/core/CardHeader';
import { CardActionArea } from '@material-ui/core';

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

const CardMedia = withStyles({
    root: {
        height: '175px',
        width: '200px',
        color: 'white',
        backgroundColor: '#4D4D4D'
    }
})(MuiCardMedia);

const CardHeader = withStyles({
    root: {
        paddingTop: '0.25pc',
        paddingBottom: '0.25pc',
        border: 0
    }
})(MuiCardHeader);


function AlbumCover(props) {
    const getAlbumCoverUrl = () =>
    {
        return `http://localhost:8081/myapp/albumImage/${props.isrc}`;
    }

    return (
      <Card>
          <CardActionArea>
              <CardMedia image={getAlbumCoverUrl()} title="Paella dish">
                 
              </CardMedia>
              <CardHeader title={props.title} subheader={props.firstname + " " + props.lastname} />
          </CardActionArea>
      </Card>
    );
}

export default AlbumCover;
