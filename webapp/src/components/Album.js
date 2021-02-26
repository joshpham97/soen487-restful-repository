import { withStyles } from '@material-ui/core/styles';
import MuiAccordion from '@material-ui/core/Accordion';
import { AccordionSummary, AccordionDetails } from '@material-ui/core';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

const Accordion = withStyles({
    root: {
        margin: 0,
        border: '1px solid rgba(0, 0, 0, 0.2)',
        boxShadow: 'none',
        '&:not(:last-child)': {
            borderBottom: 0
        },
        '&$expanded': {
            margin: 0
        }
    },
    expanded: {
        margin: 0
    }
})(MuiAccordion);

function Album(props) {
    return (
        <Accordion>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <div>
                    <b>{props.title}</b>
                    <i> by {props.firstname} {props.lastname}</i>
                </div>
            </AccordionSummary>
            <AccordionDetails>
                <div>
                    <div>ISRC: {props.isrc}</div>
                    <div>Released: {props.releaseYear}</div>
                    <div>Description: {props.contentDesc}</div>
                </div>
            </AccordionDetails>
        </Accordion>
    );
}

export default Album;
