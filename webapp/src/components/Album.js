import { makeStyles, withStyles } from '@material-ui/core/styles';
import MuiAccordion from '@material-ui/core/Accordion';
import MuiAccordionSummary from '@material-ui/core/AccordionSummary';
import MuiAccordionDetails from '@material-ui/core/AccordionDetails';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

const useStyles = makeStyles(() => ({
    headerText: {
        width: '100%',
    },
    title: {
        fontWeight: 'bold'
    },
    artist: {
        fontStyle: 'italic'
    },
    detailsHeader: {

    },
    detailsBody: {

    }
}));

const Accordion = withStyles({
    root: {
        width: '25%',
        minWidth: '200px',
        maxWidth: '300px',
        float: 'left',
        margin: 0,
        border: '1px solid rgba(0, 0, 0, 0.2)',
        borderRadius: '5px',
        boxShadow: 'none',
        // '&:not(:last-child)': {
        //     borderBottom: 0
        // },
        '&$expanded': {
            margin: 0
        }
    },
    expanded: {
        margin: 0
    }
})(MuiAccordion);

const AccordionSummary = withStyles({
    root: {
        textAlign: 'center'
    },
    expandIcon: {
        position: 'absolute',
        right: '15px'
    }
})(MuiAccordionSummary);

const AccordionDetails = withStyles({
    root: {
        textAlign: 'initial'
    }
})(MuiAccordionDetails);

function Album(props) {
    const classes = useStyles();

    return (
        <Accordion expanded={true}>
            <AccordionSummary /*expandIcon={<ExpandMoreIcon />}*/>
                <div className={classes.headerText}>
                    <div className={classes.title}>{props.title}</div>
                    <div className={classes.artist}>{props.firstname} {props.lastname}</div>
                </div>
            </AccordionSummary>
            <AccordionDetails>
                <div>
                    <div>
                        <span className={classes.detailsHeader}>ISRC: </span>
                        <span className={classes.detailsBody}>{props.isrc}</span>
                    </div>
                    <div>
                        <span className={classes.detailsHeader}>Released: </span>
                        <span className={classes.detailsBody}>{props.releaseYear}</span>
                    </div>
                    <div>
                        <span className={classes.detailsHeader}>Description: </span>
                        <span className={classes.detailsBody}>{props.contentDesc}</span>
                    </div>
                </div>
            </AccordionDetails>
        </Accordion>
    );
}

export default Album;
